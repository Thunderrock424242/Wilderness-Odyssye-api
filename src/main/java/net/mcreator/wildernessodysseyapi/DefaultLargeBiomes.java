package net.mcreator.wildernessodysseyapi;


import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import net.minecraft.world.level.levelgen.WorldGenSettings;
import net.minecraft.world.level.levelgen.WorldOptions;
import net.minecraft.world.level.levelgen.presets.WorldPresets;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ScreenEvent;

@EventBusSubscriber(modid = "yourmodid")
public class DefaultLargeBiomes {

    @SubscribeEvent
    public static void onScreenInit(ScreenEvent.Init.Post event) {
        if (event.getScreen() instanceof CreateWorldScreen createWorldScreen) {
            // Access the current world generation settings and options
            WorldGenSettings worldGenSettings = createWorldScreen.worldGenSettingsComponent().getSettings();
            WorldOptions worldOptions = worldGenSettings.worldOptions();

            // Check if the world preset is set to normal (default)
            if (worldGenSettings.presets() == WorldPresets.NORMAL) {
                // Change it to Large Biomes
                WorldGenSettings newWorldGenSettings = WorldPresets.LARGE_BIOMES.createWorldGenSettings(
                    worldOptions.seed(), worldOptions.generateStructures(), worldOptions.bonusChest());

                // Set the new world generation settings
                createWorldScreen.worldGenSettingsComponent().setSettings(newWorldGenSettings);

            }
        }
    }
}
