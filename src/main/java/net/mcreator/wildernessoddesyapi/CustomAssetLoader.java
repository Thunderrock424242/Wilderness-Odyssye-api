package net.mcreator.wildernessoddesyapi;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleResource;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.RepositorySource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class CustomAssetLoader {

    private final AssetCacheManager cacheManager;
    private final ResourceManager resourceManager;

    public CustomAssetLoader(AssetCacheManager cacheManager, ResourceManager resourceManager) {
        this.cacheManager = cacheManager;
        this.resourceManager = resourceManager;
    }

    public Resource loadResource(ResourceLocation location) throws IOException {
        String key = location.toString();
        byte[] cachedData = cacheManager.getAsset(key);

        if (cachedData != null) {
            return new SimpleResource(new Pack(key, false, () -> cachedData, new RepositorySource() {
            }, null, null), () -> new ByteArrayInputStream(cachedData));
        } else {
            Resource resource = resourceManager.getResource(location).orElseThrow(IOException::new);
            byte[] data = resource.open().readAllBytes();
            cacheManager.cacheAsset(key, data);
            return resource;
        }
    }
}
