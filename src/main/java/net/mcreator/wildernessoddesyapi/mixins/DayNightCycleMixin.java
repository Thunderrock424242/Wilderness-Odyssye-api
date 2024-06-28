package net.mcreator.wildernessoddesyapi.mixins;

import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Level.class)
public abstract class DayNightCycleMixin {

    @Redirect(method = "Lnet/minecraft/world/level/Level;tickTime()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;getDayTime()J"))
    private long modifyDayNightCycle(Level level) {
        long dayTime = level.getDayTime();
        long dayDuration = 24000L; // Vanilla Minecraft day duration
        long customDayDuration = 1000L; // Custom day duration: 5 minutes * 60 seconds * 20 ticks

        long timeOfDay = dayTime % dayDuration;
        long customTimeOfDay = (timeOfDay * customDayDuration) / dayDuration;

        return (dayTime / dayDuration) * customDayDuration + customTimeOfDay;
    }
}
