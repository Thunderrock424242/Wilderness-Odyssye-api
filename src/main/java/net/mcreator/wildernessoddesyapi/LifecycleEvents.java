// LifecycleEvents.java
package net.mcreator.wildernessoddesyapi;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.event.server.ServerStoppingEvent;

@Mod.EventBusSubscriber(modid = MyMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class LifecycleEvents {

    private static AssetCacheManager cacheManager;

    @SubscribeEvent
    public static void onServerStarting(ServerStartingEvent event) {
        cacheManager = new AssetCacheManager();
    }

    @SubscribeEvent
    public static void onServerStopping(ServerStoppingEvent event) {
        cacheManager.shutdown();
    }
}
