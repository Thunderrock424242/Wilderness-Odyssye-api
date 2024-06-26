package net.mcreator.wildernessoddesyapi;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import net.neoforged.neoforge.event.level.ChunkEvent;
import net.neoforged.neoforge.event.TickEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.ChunkStatus;

@Mod("utility_qol_for_wilderness_mod")
public class OptimizedWorldGen {
	public static final String MODID = "utility_qol_for_wilderness_mod";
	private static final Logger LOGGER = LogManager.getLogger();

	public OptimizedWorldGen() {
		// Register setup method to the mod event bus
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		
		// Register the ChunkGenerationOptimizer class with the NeoForge event bus
		NeoForge.EVENT_BUS.register(new ChunkGenerationOptimizer());
	}

	private void setup(final FMLCommonSetupEvent event) {
		LOGGER.info("Setting up mod");
		// Initialize necessary components here
	}

	public static class ChunkGenerationOptimizer {
		private static final Logger LOGGER = LogManager.getLogger();

		@SubscribeEvent
		public void onChunkLoad(ChunkEvent.Load event) {
			if (event.getChunk() instanceof LevelChunk) {
				LevelChunk chunk = (LevelChunk) event.getChunk();
				if (chunk.getLevel() instanceof ServerLevel) {
					ServerLevel world = (ServerLevel) chunk.getLevel();
					if (!world.isClientSide()) {
						if (chunk.getStatus() == ChunkStatus.FULL) {
							LOGGER.info("Chunk loaded: " + chunk.getPos());
							// Perform optimization on chunk load if necessary
							optimizeChunk(chunk);
						}
					}
				}
			}
		}

		@SubscribeEvent
		public void onServerTick(TickEvent.ServerTickEvent event) {
			// Execute only at the end phase of the tick
			if (event.phase == TickEvent.Phase.END) {
				LOGGER.debug("Server tick end");
				// Perform periodic optimization or checks
				performPeriodicTasks();
			}
		}

		private void optimizeChunk(LevelChunk chunk) {
			try {
				// Add optimization logic here
				LOGGER.info("Optimizing chunk: " + chunk.getPos());
			} catch (Exception e) {
				LOGGER.error("Error optimizing chunk: " + chunk.getPos(), e);
			}
		}

		private void performPeriodicTasks() {
			try {
				// Add periodic tasks or optimizations here
				LOGGER.debug("Performing periodic tasks");
			} catch (Exception e) {
				LOGGER.error("Error during periodic tasks", e);
			}
		}
	}
}
