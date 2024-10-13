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

import net.minecraft.world.level.biome.BiomeSourceSettings;
import net.minecraft.world.level.levelgen.WorldGenSettings;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "modid", bus = Mod.EventBusSubscriber.Bus.MOD)
public class ForceLargeBiomes {

    @SubscribeEvent
    public static void onWorldCreation(CreateWorldScreen.Event event) {
        // Force Large Biomes by default
        WorldGenSettings worldGenSettings = event.getWorldGenSettings();
        BiomeSourceSettings biomeSourceSettings = worldGenSettings.worldType().biomeSource();

        // Force large biomes and lock other options
        biomeSourceSettings.setLargeBiomes(true);

        // Optionally disable other world type options from appearing
        // Set a flag or modify UI elements to disable the world type selection menu
        event.setWorldGenSettings(worldGenSettings);
    }
}
