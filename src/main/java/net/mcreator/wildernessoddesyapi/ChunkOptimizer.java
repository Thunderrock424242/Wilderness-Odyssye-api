package net.mcreator.wildernessoddesyapi;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ChunkHolder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.server.MinecraftServer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.event.server.FMLServerStartingEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.minecraftforge.event.server.ServerTickEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.EventBus;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventListener;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Mod("chunkoptimizer")
public class ChunkOptimizer {

    public ChunkOptimizer() {
        IEventBus bus = net.neoforged.fml.event.lifecycle.FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::setup);
        net.neoforged.neoforge.common.NeoForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        // Perform common setup tasks here
    }

    @SubscribeEvent
    public void onServerTick(ServerTickEvent event) {
        if (event.phase == ServerTickEvent.Phase.END) {
            MinecraftServer server = event.getServer();
            for (ServerLevel world : server.getAllLevels()) {
                optimizeChunkLoading(world);
            }
        }
    }

    private void optimizeChunkLoading(ServerLevel world) {
        ServerChunkCache chunkCache = world.getChunkSource();
        for (ChunkHolder chunkHolder : chunkCache.chunkMap.getChunks()) {
            ChunkAccess chunkAccess = chunkHolder.getLastAvailable();
            if (chunkAccess instanceof LevelChunk && chunkAccess.getStatus() == ChunkStatus.FULL) {
                LevelChunk chunk = (LevelChunk) chunkAccess;
                boolean shouldUnload = shouldUnloadChunk(chunk, world);
                if (shouldUnload) {
                    world.getChunkSource().chunkMap.release(chunkHolder);
                }
            }
        }
    }

    private boolean shouldUnloadChunk(LevelChunk chunk, ServerLevel world) {
        // Determine if a chunk should be unloaded
        // For example, unload chunks that are far from any player
        final int unloadDistance = 8; // Adjust this distance as needed
        for (ServerPlayer player : world.players()) {
            if (chunk.getPos().getChessboardDistance(player.chunkPosition()) <= unloadDistance) {
                return false;
            }
        }
        return true;
    }

    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        detectOtherChunkOptimizers();
    }

    private void detectOtherChunkOptimizers() {
        try {
            Field listenersField = EventBus.class.getDeclaredField("listeners");
            listenersField.setAccessible(true);

            List<IEventListener> listeners = (List<IEventListener>) listenersField.get(net.neoforged.neoforge.common.NeoForge.EVENT_BUS);
            for (IEventListener listener : listeners) {
                // Check if listener is a chunk optimizer
                if (isChunkOptimizer(listener)) {
                    // Assist in optimization if detected
                    assistInOptimization(listener);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isChunkOptimizer(IEventListener listener) {
        // Implement logic to detect if a listener is a chunk optimizer
        // This can be done by checking class names, method names, etc.
        return listener.getClass().getName().contains("ChunkOptimizer");
    }

    private void assistInOptimization(IEventListener listener) {
        // Implement logic to assist in optimization
        // This might include modifying event priorities, adding hooks, etc.
        // Here, you can coordinate with the detected listener for optimized chunk loading
        System.out.println("Detected another chunk optimizer: " + listener.getClass().getName());
        // Example: Adjusting event priority
        net.neoforged.neoforge.common.NeoForge.EVENT_BUS.unregister(listener);
        net.neoforged.neoforge.common.NeoForge.EVENT_BUS.register(listener, EventPriority.LOW);
    }
}
