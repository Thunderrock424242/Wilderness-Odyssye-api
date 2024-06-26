package net.mcreator.wildernessoddesyapi;

import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.chunk.LevelChunk;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.TickEvent;
import net.neoforged.neoforge.event.world.ChunkEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(OptimizedWorldGen.MODID)
public class OptimizedWorldGen {
    public static final String MODID = "optimizedworldgen";
    private static final Logger LOGGER = LogManager.getLogger();

    public OptimizedWorldGen() {
        NeoForge.EVENT_BUS.register(this);
        NeoForge.EVENT_BUS.register(new ChunkGenerationOptimizer());
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
    }

    private void setup(final FMLCommonSetupEvent event) {
        LOGGER.info("Setting up mod");
        // Initialize necessary components here
    }

    public static class ChunkGenerationOptimizer {
        private static final Logger LOGGER = LogManager.getLogger();

        @SubscribeEvent
        public void onChunkLoad(ChunkEvent.Load event) {
            if (event.getWorld().isClientSide()) {
                return;
            }

            LevelChunk chunk = (LevelChunk) event.getChunk();
            if (chunk.getStatus() == ChunkStatus.FULL) {
                LOGGER.info("Chunk loaded: " + chunk.getPos());
                // Perform optimization on chunk load if necessary
            }
        }

        @SubscribeEvent
        public void onServerTick(TickEvent.ServerTickEvent event) {
            // Handle chunk generation tasks asynchronously to prevent freezing
            // This is a basic example, you may need more complex logic for your specific use case
            if (event.phase == TickEvent.Phase.END) {
                // Process pending chunk generation tasks
            }
        }
    }
}
