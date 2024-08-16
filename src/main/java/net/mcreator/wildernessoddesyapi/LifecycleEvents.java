package net.mcreator.wildernessoddesyapi;

import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.event.server.ServerStoppingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

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
