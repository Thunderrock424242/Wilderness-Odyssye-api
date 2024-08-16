package net.mcreator.wildernessoddesyapi;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;

import java.io.IOException;

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
            return new ByteArrayResource(cachedData);
        } else {
            Resource resource = resourceManager.getResource(location).orElseThrow(IOException::new);
            byte[] data = resource.getInputStream().readAllBytes();
            cacheManager.cacheAsset(key, data);
            return resource;
        }
    }

    private static class ByteArrayResource implements Resource {
        private final byte[] data;

        public ByteArrayResource(byte[] data) {
            this.data = data;
        }

        @Override
        public InputStream getInputStream() {
            return new ByteArrayInputStream(data);
        }

        @Override
        public String getPackName() {
            return "ByteArrayResource";
        }
    }
}
