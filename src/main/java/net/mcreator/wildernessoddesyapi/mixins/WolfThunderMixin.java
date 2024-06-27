package net.mcreator.wildernessoddesyapi.mixins;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

import net.neoforged.neoforge.event.TickEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.BlockPos;

import java.util.function.Predicate;

@Mixin(Wolf.class)
public abstract class WolfThunderMixin {
	@Shadow
	private GoalSelector goalSelector;

	@Shadow
	public abstract Level getLevel();

	@Shadow
	private Level level;

	@Inject(method = "tick", at = @At("HEAD"))
	private void onTick(CallbackInfo info) {
		if (this.getLevel().isThundering()) {
			this.goalSelector.addGoal(1, new FindShelterGoal((Wolf) (Object) this));
		}
	}

	@Mod.EventBusSubscriber
	public static class EventHandlers {
		@SubscribeEvent
		public static void onLevelTick(TickEvent.LevelTickEvent event) {
			Level level = event.level;
			if (level.isThundering()) {
				AABB area = new AABB(level.getMinBuildHeight(), 0, level.getMinBuildHeight(), level.getMaxBuildHeight(), level.getMaxBuildHeight(), level.getMaxBuildHeight());
				Predicate<Entity> wolfPredicate = entity -> entity.getType() == EntityType.WOLF && entity.isAlive();
				level.getEntities(EntityType.WOLF, area, wolfPredicate).forEach(wolf -> {
					if (wolf instanceof Wolf) {
						((Wolf) wolf).goalSelector.addGoal(1, new FindShelterGoal((Wolf) wolf));
					}
				});
			}
		}
	}

	private static class FindShelterGoal extends Goal {
		private final Wolf wolf;
		private final Level level;

		public FindShelterGoal(Wolf wolf) {
			this.wolf = wolf;
			this.level = ((WolfThunderMixin) (Object) wolf).level;
		}

		@Override
		public boolean canUse() {
			return this.level.isThundering();
		}

		@Override
		public void start() {
			// Find shelter logic
			// Example: Move towards a specific location or find a nearby shelter
			BlockPos shelterPos = findNearestShelter();
			if (shelterPos != null) {
				wolf.getNavigation().moveTo(shelterPos.getX(), shelterPos.getY(), shelterPos.getZ(), 1.0);
			}
		}

		private BlockPos findNearestShelter() {
			// Implement your logic to find the nearest shelter
			// For example, find a nearby tree or cave
			return null;
		}
	}
}
