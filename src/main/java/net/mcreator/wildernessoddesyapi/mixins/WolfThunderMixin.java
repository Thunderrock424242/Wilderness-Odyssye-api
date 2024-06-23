package net.mcreator.wildernessoddesyapi.mixins;


import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.TickEvent.LevelTickEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;

@Mixin(Wolf.class)
public abstract class WolfAiMixin extends Animal {

    @Shadow
    private GoalSelector goalSelector;

    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo info) {
        if (this.level.isThundering()) {
            this.goalSelector.addGoal(1, new FindShelterGoal((Wolf) (Object) this));
        }
    }

    @Mod.EventBusSubscriber
    public static class EventHandlers {

        @SubscribeEvent
        public static void onLevelTick(LevelTickEvent event) {
            Level level = event.getLevel();
            if (level.isThundering()) {
                level.getEntities(EntityType.WOLF, Entity::isAlive).forEach(wolf -> {
                    ((WolfAiMixin) wolf).goalSelector.addGoal(1, new FindShelterGoal((Wolf) wolf));
                });
            }
        }
    }

    private static class FindShelterGoal extends Goal {
        private final Wolf wolf;

        public FindShelterGoal(Wolf wolf) {
            this.wolf = wolf;
        }

        @Override
        public boolean canUse() {
            return wolf.level.isThundering();
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
