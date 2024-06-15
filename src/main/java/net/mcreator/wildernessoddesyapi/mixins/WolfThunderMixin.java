package net.mcreator.wildernessoddesyapi.mixins;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.world.entity.Entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.world.ThunderEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(Wolf.class)
public abstract class WolfMixin {

    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        Wolf wolf = (Wolf) (Object) this;
        Level world = wolf.level;

        if (world.isThundering() && !wolf.isInSittingPose() && !wolf.isTame()) {
            Optional<BlockPos> shelter = findShelter(world, wolf.blockPosition());

            shelter.ifPresent(pos -> {
                PathNavigation navigation = wolf.getNavigation();
                navigation.moveTo(pos.getX(), pos.getY(), pos.getZ(), 1.0);
            });
        }
    }

    private Optional<BlockPos> findShelter(Level world, BlockPos start) {
        int radius = 16;
        for (int y = -5; y < 5; y++) {
            for (int x = -radius; x < radius; x++) {
                for (int z = -radius; z < radius; z++) {
                    BlockPos pos = start.offset(x, y, z);
                    if (world.getBlockState(pos).is(Blocks.OAK_LOG) || world.getBlockState(pos).is(Blocks.STONE)) {
                        return Optional.of(pos);
                    }
                }
            }
        }
        return Optional.empty();
    }
}