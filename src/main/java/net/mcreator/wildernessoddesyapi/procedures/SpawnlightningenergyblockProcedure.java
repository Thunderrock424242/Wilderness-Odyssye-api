package net.mcreator.wildernessoddesyapi.procedures;

import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.BlockPos;

import net.mcreator.wildernessoddesyapi.init.WildernessOddesyApiModBlocks;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class SpawnlightningenergyblockProcedure {
	@SubscribeEvent
	public static void onEntitySpawned(EntityJoinLevelEvent event) {
		execute(event, event.getLevel(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), event.getEntity());
	}

	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		execute(null, world, x, y, z, entity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof LightningBolt) {
			world.setBlock(BlockPos.containing(x, y, z), WildernessOddesyApiModBlocks.LIGHTINGENERGYBLOCK.get().defaultBlockState(), 3);
		}
	}
}
