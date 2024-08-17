/*package net.mcreator.wildernessoddesyapi;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.IoSupplier;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceProvider;
import net.minecraft.server.packs.resources.SimpleResource;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.RepositorySource;

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
    public Optional<Resource> getResource(ResourceLocation location) {
        String key = location.toString();
        byte[] cachedData = cacheManager.getAsset(key);

        if (cachedData != null) {
            return Optional.of(new SimpleResource(new Pack(key, false, () -> cachedData, new RepositorySource() {
            }, null, null), () -> new ByteArrayInputStream(cachedData)));
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
*/