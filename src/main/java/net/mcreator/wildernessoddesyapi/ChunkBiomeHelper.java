package net.mcreator.wildernessoddesyapi;

import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ChunkMap;
import net.mcreator.wildernessoddesyapi.mixin.ChunkSourceAccessorMixin;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.StreamSupport;

public class ChunkBiomeHelper {

    public static Set<ResourceKey<Biome>> getBiomesInDimension(MinecraftServer server, ResourceKey<Level> dimensionKey) {
        Set<ResourceKey<Biome>> biomes = new HashSet<>();
        Level level = server.getLevel(dimensionKey);
        if (level != null) {
            try {
                ChunkSourceAccessorMixin chunkSourceAccessor = (ChunkSourceAccessorMixin) level.getChunkSource();
                ChunkMap chunkMap = chunkSourceAccessor.getChunkMap();
                Field chunksField = ChunkMap.class.getDeclaredField("visibleChunks");
                chunksField.setAccessible(true);
                Iterable<ChunkAccess> chunks = (Iterable<ChunkAccess>) chunksField.get(chunkMap);

                StreamSupport.stream(chunks.spliterator(), false)
                    .filter(chunkAccess -> chunkAccess instanceof LevelChunk)
                    .map(chunkAccess -> (LevelChunk) chunkAccess)
                    .forEach(chunk -> {
                        for (LevelChunkSection section : chunk.getSections()) {
                            section.getBiomes().getAll(biomeHolder -> biomes.add(biomeHolder.unwrapKey().orElse(null)));
                        }
                    });
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return biomes;
    }
}