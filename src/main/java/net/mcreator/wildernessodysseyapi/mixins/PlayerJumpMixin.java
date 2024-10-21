package net.mcreator.wildernessodysseyapi.mixins;

import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public class PlayerJumpMixin {

    @Inject(method = "jumpFromGround", at = @At("HEAD"), cancellable = true)
    private void modifyJumpHeight(CallbackInfo info) {
        Player player = (Player) (Object) this;
        // Example of modifying the jump height
        float customJumpFactor = 1.0F; // Double the jump height
        player.setDeltaMovement(player.getDeltaMovement().x, 0.42D * customJumpFactor, player.getDeltaMovement().z);
        info.cancel(); // Cancel the original jump behavior
    }
}