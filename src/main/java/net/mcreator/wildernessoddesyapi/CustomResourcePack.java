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

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.IoSupplier;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceProvider;
import net.minecraft.server.packs.resources.SimpleResource;
import net.minecraft.server.packs.resources.metadata.ResourceMetadata;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.repository.RepositorySource;

import java.io.ByteArrayInputStream;
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
            return Optional.of(() -> new SimpleResource(location, new ByteArrayInputStream(cachedData), () -> ResourceMetadata.EMPTY));
        } else {
            return fallbackResourceProvider.getResource(type, location).map(resource -> {
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
