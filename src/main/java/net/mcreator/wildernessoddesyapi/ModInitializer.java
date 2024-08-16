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

import net.minecraft.client.Minecraft;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.packs.repository.RepositorySource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceProvider;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.event.server.ServerStoppingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MyMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModInitializer {

    private static AssetCacheManager cacheManager;

    @SubscribeEvent
    public static void onServerStarting(ServerStartingEvent event) {
        cacheManager = new AssetCacheManager();
        MinecraftForge.EVENT_BUS.register(new ResourceManagerListener(cacheManager));
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

        resourceManager.registerReloadListener(customResourceProvider);
    }
}
