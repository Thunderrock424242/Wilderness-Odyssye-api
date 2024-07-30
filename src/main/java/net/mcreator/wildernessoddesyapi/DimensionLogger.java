package net.mcreator.wildernessoddesyapi;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Set;

@Mod(WildernessOddessyApi.MOD_ID)
public class DimensionLogger {
    private static final Logger LOGGER = LogUtils.getLogger();

    public DimensionLogger() {
        // Register the common configuration
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigHolder.COMMON_SPEC);
    }

    @SubscribeEvent
    public static void onServerStarted(ServerStartedEvent event) {
        MinecraftServer server = event.getServer();
        Set<ResourceKey<Level>> dimensions = server.levelKeys();

        // Get the path from the config and convert it to Path
        Path outputPath = Path.of(ConfigHolder.COMMON.dimensionLogPath.get());

        try {
            // Ensure the config directory exists
            if (!Files.exists(outputPath.getParent())) {
                Files.createDirectories(outputPath.getParent());
            }
            Files.writeString(outputPath, "Dimensions in the game:\n", StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            for (ResourceKey<Level> dimension : dimensions) {
                Files.writeString(outputPath, "---\n" + dimension.location().toString() + "\n", StandardOpenOption.APPEND);
                Set<ResourceKey<Biome>> biomes = ChunkBiomeHelper.getBiomesInDimension(server, dimension);
                for (ResourceKey<Biome> biome : biomes) {
                    Files.writeString(outputPath, "  - " + biome.location().toString() + "\n", StandardOpenOption.APPEND);
                }
            }
            LOGGER.info("Dimensions and biomes logged to {}", outputPath.toString());
        } catch (IOException e) {
            LOGGER.error("Failed to write dimensions to file", e);
        }
    }

    // Holder class for configuration settings
    public static class ConfigHolder {
        public static final Common COMMON;
        public static final ModConfigSpec COMMON_SPEC;

        static {
            // Pair to hold the common config and its spec
            Pair<Common, ModConfigSpec> commonSpecPair = new ModConfigSpec.Builder().configure(Common::new);
            COMMON = commonSpecPair.getLeft();
            COMMON_SPEC = commonSpecPair.getRight();
        }

        // Configuration settings for the mod
        public static class Common {
            public final ModConfigSpec.ConfigValue<String> dimensionLogPath;

            public Common(ModConfigSpec.Builder builder) {
                builder.push("general");
                dimensionLogPath = builder
                    .comment("Path to the dimensions log file")
                    .define("dimensionLogPath", "config/dimensions.txt");
                builder.pop();
            }
        }
    }
}
