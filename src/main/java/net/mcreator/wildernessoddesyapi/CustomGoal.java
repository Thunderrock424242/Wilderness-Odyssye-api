/*
 * The code of this mod element is always locked.
 *
 * You can register new events in this class too.
 *
 * If you want to make a plain independent class, create it using
 * Project Browser -> New... and make sure to make the class
 * outside net.mcreator.wildernessoddesyapi as this package is managed by MCreator.
 *
 * If you change workspace package, modid or prefix, you will need
 * to manually adapt this file to these changes or remake it.
 *
 * This class will be added in the mod root package.
*/
package net.mcreator.wildernessoddesyapi;

import net.minecraft.world.entity.ai.goal;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.api.distmarker.Dist;


public class CustomGoal extends Goal {
    private final LivingEntity entity;

    public CustomGoal(LivingEntity entity) {
        this.entity = entity;
    }

    @Override
    public boolean canUse() {
        // Define when this goal can be used
        return true;
    }

    @Override
    public void tick() {
        // Define the behavior of the goal
        entity.getNavigation().moveTo(entity.getX() + 1, entity.getY(), entity.getZ(), 1.0);
    }
}