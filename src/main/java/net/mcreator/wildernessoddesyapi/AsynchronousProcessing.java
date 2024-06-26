/*
 * The code of this mod element is always locked.
 *
 * You can register new events in this class too.
 *
 * If you want to make a plain independent class, create it using
 * Project Browser -> New... and make sure to make the class
 * outside net.mcreator.wildernessoddesyapi as this package is managed by MCreator.
 *
 * If you change workspace package, modid or prefix, you will need
 * to manually adapt this file to these changes or remake it.
 *
 * This class will be added in the mod root package.
*/
package net.mcreator.wildernessoddesyapi;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLServerStartingEvent;
import net.neoforged.bus.api.SubscribeEvent;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static net.minecraft.commands.Commands.literal;

@Mod("bigmodpack")
public class AsynchronousProcessing {

    public static final String MODID = "bigmodpack";
    private static final ExecutorService executor = Executors.newFixedThreadPool(8); // Configurable thread pool size

    public AsynchronousProcessing() {
        // Register lifecycle events
        net.neoforged.neoforge.common.NeoForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onCommonSetup(FMLCommonSetupEvent event) {
        System.out.println("Common setup for AsynchronousProcessing");
    }

    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        AsyncCommand.register(event.getServer().getCommands().getDispatcher());
    }

    public static void runAsyncTask(Runnable task) {
        CompletableFuture.runAsync(task, executor)
            .thenAccept(result -> System.out.println("Async task completed"))
            .exceptionally(throwable -> {
                throwable.printStackTrace();
                return null;
            });
    }

    public static <T> CompletableFuture<T> supplyAsyncTask(java.util.function.Supplier<T> task) {
        return CompletableFuture.supplyAsync(task, executor)
            .exceptionally(throwable -> {
                throwable.printStackTrace();
                return null;
            });
    }

    public static class TaskManager {

        public static void runTaskWithProgress(ServerPlayer player, Runnable task, int totalSteps) {
            CompletableFuture.runAsync(() -> {
                for (int i = 0; i < totalSteps; i++) {
                    task.run();
                    int progress = (int) ((i / (double) totalSteps) * 100);
                    player.sendSystemMessage(Component.literal("Task Progress: " + progress + "%"));
                    try {
                        Thread.sleep(500); // Simulate work
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }, BigModPack.executor).thenAccept(result -> 
                player.sendSystemMessage(Component.literal("Task completed!"))
            ).exceptionally(throwable -> {
                throwable.printStackTrace();
                player.sendSystemMessage(Component.literal("Task failed: " + throwable.getMessage()));
                return null;
            });
        }
    }

    public static class AsyncCommand {

        public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
            dispatcher.register(
                literal("asynctask")
                    .executes(AsyncCommand::executeAsyncTask)
            );
        }

        private static int executeAsyncTask(CommandContext<CommandSourceStack> context) {
            CommandSourceStack source = context.getSource();
            ServerPlayer player = source.getPlayerOrException();
            source.sendSuccess(Component.literal("Starting async task..."), false);

            // Run the async task with progress updates
            TaskManager.runTaskWithProgress(player, () -> {
                // Simulate a part of the long-running task
                try {
                    Thread.sleep(100); // Simulate work
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, 20); // Total steps

            return 1;
        }
    }
}
