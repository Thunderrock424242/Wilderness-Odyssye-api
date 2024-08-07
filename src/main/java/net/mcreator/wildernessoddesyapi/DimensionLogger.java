package net.mcreator.wildernessoddesyapi;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
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
        // No configuration needed, we are directly writing to the logs folder
    }

    @SubscribeEvent
    public static void onServerStarted(ServerStartedEvent event) {
        MinecraftServer server = event.getServer();
        Set<ResourceKey<Level>> dimensions = server.levelKeys();

        // Define the output path in the logs folder within the client run directory
        Path outputPath = Minecraft.getInstance().gameDirectory.toPath().resolve("logs/dimensions.txt");

        try {
            // Ensure the logs directory exists
            if (!Files.exists(outputPath.getParent())) {
                Files.createDirectories(outputPath.getParent());
            }
            Files.writeString(outputPath, "Dimensions in the game:\n", StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            for (ResourceKey<Level> dimension : dimensions) {
                Files.writeString(outputPath, "---\n" + dimension.location().toString() + "\n", StandardOpenOption.APPEND);
            }
            LOGGER.info("Dimensions logged to {}", outputPath.toString());
        } catch (IOException e) {
            LOGGER.error("Failed to write dimensions to file", e);
        }
    }
}
