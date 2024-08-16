// CustomResourcePack.java
package net.mcreator.wildernessoddesyapi;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.IoSupplier;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceProvider;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

public class CustomResourcePack implements ResourceProvider {

    private final AssetCacheManager cacheManager;
    private final ResourceProvider fallbackResourceProvider;

    public CustomResourcePack(AssetCacheManager cacheManager, ResourceProvider fallbackResourceProvider) {
        this.cacheManager = cacheManager;
        this.fallbackResourceProvider = fallbackResourceProvider;
    }

    @Override
    public Optional<IoSupplier<Resource>> getResource(PackType type, ResourceLocation location) {
        String key = location.toString();
        byte[] cachedData = cacheManager.getAsset(key);

        if (cachedData != null) {
            return Optional.of(() -> new Resource() {
                @Override
                public InputStream open() throws IOException {
                    return new ByteArrayInputStream(cachedData);
                }

                @Override
                public String getSourceName() {
                    return "CustomResourcePack";
                }
            });
        } else {
            return fallbackResourceProvider.getResource(location).map(resource -> {
                try (InputStream stream = resource.open()) {
                    byte[] data = stream.readAllBytes();
                    cacheManager.cacheAsset(key, data);
                    return resource;
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            });
        }
    }
}
