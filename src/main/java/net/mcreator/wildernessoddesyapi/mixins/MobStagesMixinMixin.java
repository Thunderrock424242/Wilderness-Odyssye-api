package net.mcreator.wildernessoddesyapi.mixins;

import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.TickEvent;
import net.mcreator.wildernessoddesyapi.MobStages;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Level.class)
public class MobStagesMixinMixin {
    @Inject(method = "tick", at = @At("HEAD"))
    private void onWorldTick(CallbackInfo ci) {
        Level world = (Level) (Object) this;
        if (world.dimension() == Level.OVERWORLD && !world.isClientSide) {
            if (world.getDayTime() % 24000 == 0) { // Check if it's a new day
                MobStages.daysElapsed++;
            }
        }
    }
}
