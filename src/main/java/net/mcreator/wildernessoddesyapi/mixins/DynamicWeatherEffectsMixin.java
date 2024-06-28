package net.mcreator.wildernessoddesyapi.mixins;

import net.mcreator.wildernessoddesyapi.accessor.LevelAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

@Mixin({Animal.class, Wolf.class})
public abstract class DynamicWeatherEffectsMixin extends PathfinderMob {

    protected DynamicWeatherEffectsMixin(EntityType<? extends PathfinderMob> type, Level level) {
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
            Level level = ((LevelAccessor) this.animal).getLevel();
            return level.isRaining() || level.isThundering();
        }

        @Override
        public void start() {
            BlockPos shelter = this.findShelter();
            if (shelter != null) {
                this.animal.getNavigation().moveTo(shelter.getX(), shelter.getY(), shelter.getZ(), 1.0D);
            }
        }

        @Override
        public boolean canContinueToUse() {
            Level level = ((LevelAccessor) this.animal).getLevel();
            return level.isRaining() || level.isThundering();
        }

        private BlockPos findShelter() {
            Level level = ((LevelAccessor) this.animal).getLevel();
            BlockPos currentPos = this.animal.blockPosition();

            BlockPos treeShelter = findTreeShelter(level, currentPos);
            if (treeShelter != null) {
                return treeShelter;
            }

            BlockPos caveShelter = findCaveShelter(level, currentPos);
            if (caveShelter != null) {
                return caveShelter;
            }

            BlockPos structureShelter = findStructureShelter(level, currentPos);
            if (structureShelter != null) {
                return structureShelter;
            }

            return null;
        }

        private BlockPos findTreeShelter(Level level, BlockPos currentPos) {
            Set<BlockState> leafBlocks = new HashSet<>();
            leafBlocks.add(Blocks.OAK_LEAVES.defaultBlockState());
            leafBlocks.add(Blocks.SPRUCE_LEAVES.defaultBlockState());
            leafBlocks.add(Blocks.BIRCH_LEAVES.defaultBlockState());
            leafBlocks.add(Blocks.JUNGLE_LEAVES.defaultBlockState());
            leafBlocks.add(Blocks.ACACIA_LEAVES.defaultBlockState());
            leafBlocks.add(Blocks.DARK_OAK_LEAVES.defaultBlockState());
            leafBlocks.add(Blocks.MANGROVE_LEAVES.defaultBlockState());

            for (int dx = -5; dx <= 5; dx++) {
                for (int dz = -5; dz <= 5; dz++) {
                    BlockPos pos = currentPos.offset(dx, 0, dz);
                    if (leafBlocks.contains(level.getBlockState(pos.above()))) {
                        return pos;
                    }
                }
            }
            return null;
        }

        private BlockPos findCaveShelter(Level level, BlockPos currentPos) {
            for (int dx = -10; dx <= 10; dx++) {
                for (int dz = -10; dz <= 10; dz++) {
                    for (int dy = -5; dy <= 5; dy++) {
                        BlockPos pos = currentPos.offset(dx, dy, dz);
                        if (level.getBlockState(pos).isAir() && isCave(level, pos)) {
                            return pos;
                        }
                    }
                }
            }
            return null;
        }

        private boolean isCave(Level level, BlockPos pos) {
            int solidSides = 0;
            if (level.getBlockState(pos.north()).isSolidRender(level, pos.north())) solidSides++;
            if (level.getBlockState(pos.south()).isSolidRender(level, pos.south())) solidSides++;
            if (level.getBlockState(pos.west()).isSolidRender(level, pos.west())) solidSides++;
            if (level.getBlockState(pos.east()).isSolidRender(level, pos.east())) solidSides++;
            return solidSides >= 3;
        }

        private BlockPos findStructureShelter(Level level, BlockPos currentPos) {
            for (int dx = -10; dx <= 10; dx++) {
                for (int dz = -10; dz <= 10; dz++) {
                    for (int dy = -5; dy <= 5; dy++) {
                        BlockPos pos = currentPos.offset(dx, dy, dz);
                        if (level.getBlockState(pos).isSolidRender(level, pos) && isStructure(level, pos)) {
                            return pos;
                        }
                    }
                }
            }
            return null;
        }

        private boolean isStructure(Level level, BlockPos pos) {
            return level.getBlockState(pos).is(Blocks.BRICKS); // Simplified example, adjust as needed
        }
    }
}
