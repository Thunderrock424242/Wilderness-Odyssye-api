/*
 * The code of this mod element is always locked.
 *
 * You can register new events in this class too.
 *
 * If you want to make a plain independent class, create it using
 * Project Browser -> New... and make sure to make the class
 * outside net.mcreator.wildernessodysseyapi as this package is managed by MCreator.
 *
 * If you change workspace package, modid or prefix, you will need
 * to manually adapt this file to these changes or remake it.
 *
 * This class will be added in the mod root package.
*/
package net.mcreator.wildernessodysseyapi;

import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import net.minecraft.world.level.levelgen.WorldGenSettings;
import net.minecraft.world.level.levelgen.presets.WorldPreset;
import net.minecraft.world.level.levelgen.presets.WorldPresets;
import net.neoforged.fml.common.Mod;
import net.neoforged.bus.api.SubscribeEvent;

@Mod.EventBusSubscriber(modid = "modid", bus = Mod.EventBusSubscriber.Bus.MOD)
public class DefaultLargeBiomes {

    @SubscribeEvent
    public static void onWorldCreation(CreateWorldScreen.CreateLevelEvent event) {
        // Get the current world generation settings
        WorldGenSettings worldGenSettings = event.getWorldGenSettings();
        
        // Check if the world type is not set or is the default type, and set Large Biomes instead
        if (worldGenSettings.preset() == WorldPresets.NORMAL) {
            worldGenSettings = WorldPreset.LARGE_BIOMES.createWorldGenSettings(event.getSeed(), event.getOptions().generateStructures(), event.getOptions().bonusChest());
            event.setWorldGenSettings(worldGenSettings);
        }
    }
}
