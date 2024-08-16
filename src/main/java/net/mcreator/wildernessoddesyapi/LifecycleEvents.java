// LifecycleEvents.java
package net.mcreator.wildernessoddesyapi;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.server.FMLServerStartingEvent;
import net.neoforged.fml.event.server.FMLServerStoppingEvent;

@Mod.EventBusSubscriber(modid = MyMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class LifecycleEvents {

    private static AssetCacheManager cacheManager;

    @SubscribeEvent
    public static void onServerStarting(FMLServerStartingEvent event) {
        cacheManager = new AssetCacheManager();
    }

    @SubscribeEvent
    public static void onServerStopping(FMLServerStoppingEvent event) {
        cacheManager.shutdown();
    }
}
