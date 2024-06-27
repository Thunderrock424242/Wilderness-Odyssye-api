package net.mcreator.wildernessoddesyapi.mixins;

import net.mcreator.wildernessoddesyapi.accessor.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Entity.class)
public abstract class EntityMixinMixin implements LevelAccessor {

    @Shadow
    private Level level;

    @Override
    public Level getLevel() {
        return this.level;
    }
}
