package net.mcreator.wildernessoddesyapi.mixins;

import net.mcreator.wildernessoddesyapi.accessor.LevelAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.TickEvent;
import net.neoforged.bus.api.SubscribeEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.EnumSet;

@Mixin(Animal.class)
public abstract class DynamicWeatherEffectsMixin extends PathfinderMob {

    public DynamicWeatherEffectsMixin(EntityType<? extends PathfinderMob> type, Level level) {
        super(type, level);
    }

    @Inject(method = "registerGoals", at = @At("HEAD"))
    private void addShelterSeekingGoal(CallbackInfo info) {
        this.goalSelector.addGoal(1, new SeekShelterDuringStormGoal((Animal) (Object) this));
    }

    private static class SeekShelterDuringStormGoal extends Goal {
        private final Animal animal;

        public SeekShelterDuringStormGoal(Animal animal) {
            this.animal = animal;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            return ((LevelAccessor) this.animal).getLevel().isThundering();
        }

        @Override
        public void start() {
            this.animal.getNavigation().moveTo(this.findShelter().getX(), this.findShelter().getY(), this.findShelter().getZ(), 1.0D);
        }

        private BlockPos findShelter() {
            return new BlockPos((int) this.animal.getX(), (int) this.animal.getY(), (int) this.animal.getZ());
        }

        @Override
        public boolean canContinueToUse() {
            return ((LevelAccessor) this.animal).getLevel().isThundering();
        }
    }
}
