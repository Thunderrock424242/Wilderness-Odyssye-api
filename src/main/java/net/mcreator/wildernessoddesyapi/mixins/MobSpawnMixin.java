package net.mcreator.wildernessoddesyapi.mixins;

import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.event.entity.living.MobSpawnEvent;
import net.mcreator.wildernessoddesyapi.MobStages;
import net.mcreator.wildernessoddesyapi.MobStages.ModConfigHolder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityType.class)
public class MobSpawnMixin {
    @Inject(method = "canSpawn", at = @At("HEAD"), cancellable = true)
    private void onMobSpawn(MobSpawnEvent.SpawnPlacementCheck event, CallbackInfoReturnable<MobSpawnEvent.SpawnPlacementCheck.Result> cir) {
        if (event.getEntityType().getCategory() == MobCategory.MONSTER) {
            Level world = (Level) event.getLevel();
            if (world.dimension() == Level.OVERWORLD) {
                if (MobStages.daysElapsed <= 20) {
                    int additionalMobs = MobStages.daysElapsed / MobStages.BASE_MOB_SPAWN_RATE;
                    int maxMobs = ModConfigHolder.COMMON.maxMobs.get();
                    if (world.random.nextInt(100) < additionalMobs && additionalMobs < maxMobs) {
                        cir.setReturnValue(MobSpawnEvent.SpawnPlacementCheck.Result.ALLOW);
                    }
                } else {
                    // Revert to Minecraft's normal spawning system after 20 days
                    cir.setReturnValue(MobSpawnEvent.SpawnPlacementCheck.Result.DEFAULT);
                }
            }
        }
    }
}
