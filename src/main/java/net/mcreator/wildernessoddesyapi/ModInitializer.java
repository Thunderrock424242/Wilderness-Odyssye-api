package net.mcreator.wildernessoddesyapi;

import net.minecraft.client.Minecraft;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceProvider;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.event.server.ServerStoppingEvent;

@EventBusSubscriber(bus = Bus.MOD, modid = "wilderness_oddesy_api")
public class ModInitializer {

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

class ResourceManagerListener {
    private final AssetCacheManager cacheManager;

    public ResourceManagerListener(AssetCacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @SubscribeEvent
    public void onResourceManagerReload(RegisterClientReloadListenersEvent event) {
        ResourceManager resourceManager = Minecraft.getInstance().getResourceManager();

        ResourceProvider defaultProvider = resourceManager::getResource;

        ResourceProvider customResourceProvider = new CustomResourcePack(cacheManager, defaultProvider);

        // The method below is assumed to be valid. Adjust it according to NeoForge's API.
        // resourceManager.registerReloadListener(customResourceProvider); 
    }
}
