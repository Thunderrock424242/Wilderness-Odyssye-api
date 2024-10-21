package net.mcreator.wildernessodysseyapi.mixins;

import net.minecraft.world.food.FoodData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FoodData.class)
public class HungerMixin {

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void modifyHungerDrain(CallbackInfo info) {
        FoodData foodData = (FoodData) (Object) this;

        // Example: Increase hunger drain by 1.5x
        if (foodData.getFoodLevel() > 0) {
            int drainRate = foodData.getFoodLevel() > 10 ? 2 : 3; // Different rates based on current level
            foodData.setFoodLevel(foodData.getFoodLevel() - drainRate);
            info.cancel(); // Cancel original hunger drain to apply custom logic
        }
    }
}