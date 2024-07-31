package net.mcreator.wildernessoddesyapi.mixin;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.entity.MobSpawnType;

import net.mcreator.wildernessoddesyapi.MobStages.ModConfigHolder;
import net.mcreator.wildernessoddesyapi.MobStages;

@Mixin(EntityType.class)
public class MobSpawnMixin {

    @Inject(method = "canSpawn(Lnet/minecraft/world/entity/EntityType;Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/world/entity/MobSpawnType;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/ServerLevelAccessor;)Z", at = @At("HEAD"), cancellable = true)
    private static void onMobSpawn(EntityType<?> entityType, LevelReader levelReader, MobSpawnType spawnType, BlockPos pos, ServerLevelAccessor serverLevelAccessor, CallbackInfoReturnable<Boolean> cir) {
        // Check if the entity type is a monster and if the world is the Overworld
        if (entityType.getCategory() == MobCategory.MONSTER) {
            if (levelReader instanceof Level && ((Level) levelReader).dimension().location().equals(Level.OVERWORLD.location())) {
                // Apply custom spawning logic for the first 20 days
                if (MobStages.daysElapsed <= 20) {
                    int additionalMobs = MobStages.daysElapsed / MobStages.BASE_MOB_SPAWN_RATE;
                    int maxMobs = ModConfigHolder.COMMON.maxMobs.get();
                    // Allow additional mob spawns based on the custom logic
                    if (((Level) levelReader).random.nextInt(100) < additionalMobs && additionalMobs < maxMobs) {
                        cir.setReturnValue(true); // Allow spawn
                        return;
                    }
                }
                // Revert to the default spawning system after 20 days
                cir.setReturnValue(false); // Use default spawn logic
            }
        }
    }
}
