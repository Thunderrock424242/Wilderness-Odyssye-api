package net.mcreator.wildernessoddesyapi;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.Level;
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
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigHolder.COMMON_SPEC);
    }

    @SubscribeEvent
    public static void onServerStarted(ServerStartedEvent event) {
        MinecraftServer server = event.getServer();
        Set<ResourceKey<Level>> dimensions = server.levelKeys();

        Path outputPath = Path.of(ConfigHolder.COMMON.dimensionLogPath.get());

        try {
            if (!Files.exists(outputPath.getParent())) {
                Files.createDirectories(outputPath.getParent());
            }
            Files.writeString(outputPath, "Dimensions in the game:\n", StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            for (ResourceKey<Level> dimension : dimensions) {
                Files.writeString(outputPath, dimension.location().toString() + "\n", StandardOpenOption.APPEND);
            }
            LOGGER.info("Dimensions logged to {}", outputPath.toString());
        } catch (IOException e) {
            LOGGER.error("Failed to write dimensions to file", e);
        }
    }

    public static class ConfigHolder {
        public static final Common COMMON;
        public static final ModConfigSpec COMMON_SPEC;

        static {
            Pair<Common, ModConfigSpec> commonSpecPair = new ModConfigSpec.Builder().configure(Common::new);
            COMMON = commonSpecPair.getLeft();
            COMMON_SPEC = commonSpecPair.getRight();
        }

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
