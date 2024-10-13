package net.mcreator.wildernessodysseyapi;

import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.biome.AmbientParticleSettings;
import net.neoforged.neoforge.common.world.BiomeModifiers;

public class NetherBiomes {
    public static final ResourceKey<Biome> NETHER_CAVE_BIOME = ResourceKey.create(Registries.BIOME, new ResourceLocation("wildernessodysseyapi:nether_cave"));
    public static final ResourceKey<Biome> CRIMSON_FOREST_BIOME = ResourceKey.create(Registries.BIOME, new ResourceLocation("wildernessodysseyapi:crimson_forest_cave"));
    public static final ResourceKey<Biome> WARPED_FOREST_BIOME = ResourceKey.create(Registries.BIOME, new ResourceLocation("wildernessodysseyapi:warped_forest_cave"));

    public static void createBiomes() {
        // Register the base Nether Cave biome
        registerNetherCaveBiome();
        // Register Crimson and Warped Forest biomes
        registerCrimsonForestBiome();
        registerWarpedForestBiome();
    }

    private static void registerNetherCaveBiome() {
        BiomeSpecialEffects effects = new BiomeSpecialEffects.Builder()
                .fogColor(0x0A0A0A)
                .waterColor(0x3F76E4)
                .waterFogColor(0x50533)
                .skyColor(0x000000)
                .build();

        BiomeGenerationSettings.Builder generationSettings = new BiomeGenerationSettings.Builder();
        // Add basic Nether-like features here (lava lakes, netherrack, etc.)

        MobSpawnSettings.Builder spawnSettings = new MobSpawnSettings.Builder();
        // You can configure Nether mobs like Ghasts, Blazes, etc. to spawn here

        Biome netherCaveBiome = new Biome.BiomeBuilder()
                .specialEffects(effects)
                .mobSpawnSettings(spawnSettings.build())
                .generationSettings(generationSettings.build())
                .temperature(2.0F)
                .downfall(0.0F)
                .build();

        BiomeModifiers.registerBiome(NETHER_CAVE_BIOME, netherCaveBiome);
    }

    private static void registerCrimsonForestBiome() {
        BiomeSpecialEffects effects = new BiomeSpecialEffects.Builder()
                .fogColor(0x330000)  // Dark red fog
                .waterColor(0x3F76E4)
                .waterFogColor(0x50533)
                .skyColor(0x330000)  // Matching red sky
                .foliageColorOverride(0xFF0000)  // Red foliage
                .grassColorOverride(0xFF3300)
                .ambientParticle(new BiomeSpecialEffects.AmbientParticleSettings(ParticleTypes.CRIMSON_SPORE, 0.01F))  // Crimson spores
                .backgroundMusic(new Music(SoundEvents.MUSIC_BIOME_CRIMSON_FOREST, 12000, 24000, true))
                .build();

        BiomeGenerationSettings.Builder generationSettings = new BiomeGenerationSettings.Builder();
        // Add crimson forest trees, blocks, and features
        generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacedFeatures.CRIMSON_TREES);

        MobSpawnSettings.Builder spawnSettings = new MobSpawnSettings.Builder();
        // Add mobs like Piglins and Hoglins
        spawnSettings.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.PIGLIN, 10, 1, 3));
        spawnSettings.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.HOGLIN, 5, 1, 2));

        Biome crimsonForestCaveBiome = new Biome.BiomeBuilder()
                .specialEffects(effects)
                .mobSpawnSettings(spawnSettings.build())
                .generationSettings(generationSettings.build())
                .temperature(2.0F)
                .downfall(0.0F)
                .build();

        BiomeModifiers.registerBiome(CRIMSON_FOREST_BIOME, crimsonForestCaveBiome);
    }

    private static void registerWarpedForestBiome() {
        BiomeSpecialEffects effects = new BiomeSpecialEffects.Builder()
                .fogColor(0x330066)  // Dark blue-purple fog
                .waterColor(0x3F76E4)
                .waterFogColor(0x50533)
                .skyColor(0x330066)  // Matching purple-blue sky
                .foliageColorOverride(0x00FFCC)  // Blue foliage
                .grassColorOverride(0x00FFFF)
                .ambientParticle(new BiomeSpecialEffects.AmbientParticleSettings(ParticleTypes.WARPED_SPORE, 0.01F))  // Warped spores
                .backgroundMusic(new Music(SoundEvents.MUSIC_BIOME_WARPED_FOREST, 12000, 24000, true))
                .build();

        BiomeGenerationSettings.Builder generationSettings = new BiomeGenerationSettings.Builder();
        // Add warped forest trees, blocks, and features
        generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacedFeatures.WARPED_TREES);

        MobSpawnSettings.Builder spawnSettings = new MobSpawnSettings.Builder();
        // Add mobs like Endermen and Striders
        spawnSettings.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ENDERMAN, 10, 1, 4));

        Biome warpedForestCaveBiome = new Biome.BiomeBuilder()
                .specialEffects(effects)
                .mobSpawnSettings(spawnSettings.build())
                .generationSettings(generationSettings.build())
                .temperature(2.0F)
                .downfall(0.0F)
                .build();

        BiomeModifiers.registerBiome(WARPED_FOREST_BIOME, warpedForestCaveBiome);
    }
}
