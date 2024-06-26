package net.mcreator.wildernessoddesyapi;

import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.TickEvent;

import java.util.concurrent.CompletableFuture;

@Mod("wilderness_oddesy_api")
public class AsynchronousProcessing {
    public static final String MODID = "wilderness_oddesy_api";

    public AsynchronousProcessing() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Common setup code
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        // Client setup code
    }

    @Mod.EventBusSubscriber(modid = AsynchronousProcessing.MODID)
    public static class EventListener {

        @SubscribeEvent
        public static void onServerTick(TickEvent.ServerTickEvent event) {
            if (event.phase == TickEvent.Phase.END) {
                // Offload task to a separate thread
                CompletableFuture.runAsync(() -> {
                    performHeavyTask();
                }).thenAccept(result -> {
                    // Optional: Handle the result on the main thread if needed
                });
            }
        }

        private static void performHeavyTask() {
            // Simulate a heavy task
            try {
                System.out.println("Heavy task started");
                Thread.sleep(5000); // Simulate delay
                System.out.println("Heavy task completed");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
