package net.mcreator.wildernessoddesyapi;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static net.minecraft.commands.Commands.literal;

@Mod("wilderness_oddesy_api")
public class AsynchronousProcessing {

    public static final String MODID = "wilderness_oddesy_api";
    private static final Logger LOGGER = LogManager.getLogger();
    private static final ExecutorService executor = Executors.newFixedThreadPool(getThreadPoolSize());

    public AsynchronousProcessing() {
        // Register lifecycle events to the mod event bus
        FMLJavaModLoadingContext.get().getModEventBus().register(this);
        // Register server events to the general event bus
        net.neoforged.neoforge.common.NeoForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onCommonSetup(FMLCommonSetupEvent event) {
        LOGGER.info("Common setup for AsynchronousProcessing");
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        AsyncCommand.register(event.getServer().getCommands().getDispatcher());
    }

    public static void runAsyncTask(Runnable task) {
        CompletableFuture.runAsync(task, executor)
            .thenAccept(result -> LOGGER.info("Async task completed"))
            .exceptionally(throwable -> {
                LOGGER.error("Async task failed", throwable);
                return null;
            });
    }

    public static <T> CompletableFuture<T> supplyAsyncTask(java.util.function.Supplier<T> task) {
        return CompletableFuture.supplyAsync(task, executor)
            .exceptionally(throwable -> {
                LOGGER.error("Async task failed", throwable);
                return null;
            });
    }

    public static void shutdownExecutor() {
        try {
            LOGGER.info("Shutting down executor service");
            executor.shutdown();
            if (!executor.awaitTermination(60, java.util.concurrent.TimeUnit.SECONDS)) {
                executor.shutdownNow();
                if (!executor.awaitTermination(60, java.util.concurrent.TimeUnit.SECONDS)) {
                    LOGGER.error("Executor service did not terminate");
                }
            }
        } catch (InterruptedException ie) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    private static int getThreadPoolSize() {
        // Retrieve the thread pool size from configuration or use a default value
        return 8;
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
                        LOGGER.error("Task interrupted", e);
                        Thread.currentThread().interrupt();
                    }
                }
            }, executor)
            .thenAccept(result -> player.sendSystemMessage(Component.literal("Task completed!")))
            .exceptionally(throwable -> {
                LOGGER.error("Task failed", throwable);
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
            ServerPlayer player;
            try {
                player = source.getPlayerOrException();
            } catch (CommandSyntaxException e) {
                source.sendFailure(Component.literal("No player found or invalid player context."));
                LOGGER.error("No player found or invalid player context", e);
                return 0;
            }
            source.sendSuccess(() -> Component.literal("Starting async task..."), false);

            // Run the async task with progress updates
            TaskManager.runTaskWithProgress(player, () -> {
                // Simulate a part of the long-running task
                try {
                    Thread.sleep(100); // Simulate work
                } catch (InterruptedException e) {
                    LOGGER.error("Task interrupted", e);
                    Thread.currentThread().interrupt();
                }
            }, 20); // Total steps

            return 1;
        }
    }
}
