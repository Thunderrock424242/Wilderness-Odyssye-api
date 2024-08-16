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

import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.event.server.ServerStoppingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLServerStartingEvent;
import net.minecraftforge.fml.event.lifecycle.FMLServerStoppingEvent;

@Mod.EventBusSubscriber(modid = MyMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class LifecycleEvents {

    private static AssetCacheManager cacheManager;

    @SubscribeEvent
    public static void onServerStarting(FMLServerStartingEvent event) {
        cacheManager = new AssetCacheManager();
        // Register the custom asset loader here, e.g., with a ResourceManager
    }

    @SubscribeEvent
    public static void onServerStopping(FMLServerStoppingEvent event) {
        cacheManager.shutdown();
    }

    @SubscribeEvent
    public static void onModConfigChanged(ModConfig.ModConfigEvent event) {
        cacheManager.invalidateCache();
    }

    @SubscribeEvent
    public static void onResourcePackReload(ResourceManagerReloadEvent event) {
        cacheManager.invalidateCache();
    }
}
