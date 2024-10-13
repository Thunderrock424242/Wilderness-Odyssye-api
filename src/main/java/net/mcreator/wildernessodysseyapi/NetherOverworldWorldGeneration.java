package net.mcreator.wildernessodysseyapi;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.world.BiomeModifiers;

public class NetherOverworldWorldGeneration {

    public static void addBiomeFeatures() {
        // Nether-style cave biome features
        addBiomeFeature(NetherBiomes.CRIMSON_FOREST_BIOME, ModPlacedFeatures.CRIMSON_TREES);
        addBiomeFeature(NetherBiomes.WARPED_FOREST_BIOME, ModPlacedFeatures.WARPED_TREES);
    }

    private static void addBiomeFeature(ResourceKey<Biome> biome, Holder<PlacedFeature> feature) {
        // Register the trees as part of the vegetation generation step for the specific biomes
        BiomeModifiers.addFeatureToBiome(biome, GenerationStep.Decoration.VEGETAL_DECORATION, feature);
    }
}
