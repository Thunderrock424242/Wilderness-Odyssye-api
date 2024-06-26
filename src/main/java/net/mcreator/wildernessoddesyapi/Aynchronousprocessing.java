package net.mcreator.wildernessoddesyapi;

import net.neoforged.fml.common.Mod;
import net.neoforged.api.eventbus.api.SubscribeEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.api.fml.event.server.FMLServerStoppingEvent;
import net.neoforged.neoforge.common.NeoForge;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@Mod("offloadmod")
public class AsynchronousProcessing {

    private static final ExecutorService executor = Executors.newFixedThreadPool(4);

    public AsynchronousProcessing() {
        // Register the setup method for modloading
        NeoForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onCommonSetup(FMLCommonSetupEvent event) {
        // Start an example heavy computation on a separate thread
        executor.submit(this::heavyComputation);
        // Perform file I/O on a separate thread
        performFileIO();
    }

    private void heavyComputation() {
        // Simulate heavy computation
        try {
            System.out.println("Starting heavy computation...");
            Thread.sleep(5000); // Simulate a long computation
            System.out.println("Heavy computation completed.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Heavy computation interrupted.");
        }
    }

    private void performFileIO() {
        Future<String> future = executor.submit(new Callable<String>() {
            @Override
            public String call() throws IOException {
                StringBuilder result = new StringBuilder();
                try (BufferedReader reader = new BufferedReader(new FileReader("example.txt"))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line).append("\n");
                    }
                }
                return result.toString();
            }
        });

        executor.submit(() -> {
            try {
                String fileContent = future.get();
                System.out.println("File content read asynchronously: " + fileContent);
            } catch (Exception e) {
                System.err.println("Failed to read file asynchronously: " + e.getMessage());
            }
        });
    }

    @SubscribeEvent
    public void onServerStopping(FMLServerStoppingEvent event) {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
                if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                    System.err.println("Executor service did not terminate.");
                }
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
