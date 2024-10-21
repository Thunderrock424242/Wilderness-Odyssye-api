package net.mcreator.wildernessodysseyapi.mixins;

import net.minecraft.world.food.FoodData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FoodData.class)
public class HungerMixin {

    private int tickCounter = 0; // Custom counter for managing slower hunger drain

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void modifyHungerDrain(CallbackInfo info) {
        FoodData foodData = (FoodData) (Object) this;
        tickCounter++;

        // Example: Slow down hunger drain by reducing its frequency
        if (foodData.getFoodLevel() > 0) {
            int slowerRate = foodData.getFoodLevel() > 10 ? 1 : 2; // Different rates based on current level

            // Adjust hunger drain frequency (e.g., once every 5 ticks)
            if (tickCounter >= 5) {
                foodData.setFoodLevel(foodData.getFoodLevel() - slowerRate);
                tickCounter = 0; // Reset the counter after draining
                info.cancel(); // Cancel the original hunger drain logic
            }
        }
    }
}
