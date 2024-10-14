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
import net.minecraft.world.level.levelgen.WorldOptions;
import net.minecraft.world.level.levelgen.presets.WorldPresets;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
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

                // Notify the player in the chat
                Minecraft minecraft = createWorldScreen.getMinecraft();
                LocalPlayer player = minecraft.player;
                if (player != null) {
                    player.displayClientMessage(Component.literal("Default world type set to Large Biomes!"), false);
                }
            }
        }
    }
}
