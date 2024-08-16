// ModInitializer.java
package net.mcreator.wildernessoddesyapi;

import net.minecraft.client.Minecraft;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.packs.repository.RepositorySource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceProvider;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.client.event.RegisterClientReloadListenersEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.server.FMLServerStartingEvent;
import net.neoforged.fml.event.server.FMLServerStoppingEvent;

@Mod.EventBusSubscriber(modid = MyMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModInitializer {

    private static AssetCacheManager cacheManager;

    @SubscribeEvent
    public static void onServerStarting(FMLServerStartingEvent event) {
        cacheManager = new AssetCacheManager();
        NeoForge.EVENT_BUS.register(new ResourceManagerListener(cacheManager));
    }

    @SubscribeEvent
    public static void onServerStopping(FMLServerStoppingEvent event) {
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

        resourceManager.registerReloadListener(customResourceProvider);
    }
}
