package net.mcreator.wildernessodysseyapi;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.RandomSpreadFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class ModPlacedFeatures {
    public static final Holder<PlacedFeature> CRIMSON_TREES = registerCrimsonForestTrees();
    public static final Holder<PlacedFeature> WARPED_TREES = registerWarpedForestTrees();

    private static Holder<PlacedFeature> registerCrimsonForestTrees() {
        // Define the Crimson Forest tree features
        ConfiguredFeature<TreeConfiguration, ?> crimsonTree = Feature.TREE.configured((new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(Blocks.CRIMSON_STEM.defaultBlockState()),
                new StraightTrunkPlacer(5, 2, 2), // Height of crimson trees
                BlockStateProvider.simple(Blocks.CRIMSON_NYLIUM.defaultBlockState()),
                new RandomSpreadFoliagePlacer(3, 0, 2, 2),
                new TwoLayersFeatureSize(1, 0, 1))).build());

        return new PlacedFeature(crimsonTree, PlacementUtils.treePlacementModifiers(Heightmap.Types.MOTION_BLOCKING, 6));
    }

    private static Holder<PlacedFeature> registerWarpedForestTrees() {
        // Define the Warped Forest tree features
        ConfiguredFeature<TreeConfiguration, ?> warpedTree = Feature.TREE.configured((new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(Blocks.WARPED_STEM.defaultBlockState()),
                new StraightTrunkPlacer(5, 2, 2), // Height of warped trees
                BlockStateProvider.simple(Blocks.WARPED_NYLIUM.defaultBlockState()),
                new RandomSpreadFoliagePlacer(3, 0, 2, 2),
                new TwoLayersFeatureSize(1, 0, 1))).build());

        return new PlacedFeature(warpedTree, PlacementUtils.treePlacementModifiers(Heightmap.Types.MOTION_BLOCKING, 6));
    }
}
