/*
 * The code of this mod element is always locked.
 *
 * You can register new events in this class too.
 *
 * If you want to make a plain independent class, create it using
 * Project Browser -> New... and make sure to make the class
 * outside net.mcreator.wildernessodysseyapi as this package is managed by MCreator.
 *
 * If you change workspace package, modid or prefix, you will need
 * to manually adapt this file to these changes or remake it.
 *
 * This class will be added in the mod root package.
*/
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
