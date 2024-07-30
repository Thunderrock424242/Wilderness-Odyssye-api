package net.mcreator.wildernessoddesyapi;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.LevelResource;
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
public class Dimensionlogger {
    private static final Logger LOGGER = LogUtils.getLogger();

    @SubscribeEvent
    public static void onServerStarted(ServerStartedEvent event) {
        MinecraftServer server = event.getServer();
        Set<ResourceKey<Level>> dimensions = server.levelKeys();
        Path outputPath = server.getWorldPath(LevelResource.ROOT).resolve("dimensions.txt");

        try {
            Files.writeString(outputPath, "Dimensions in the game:\n", StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            for (ResourceKey<Level> dimension : dimensions) {
                Files.writeString(outputPath, dimension.location().toString() + "\n", StandardOpenOption.APPEND);
            }
            LOGGER.info("Dimensions logged to {}", outputPath.toString());
        } catch (IOException e) {
            LOGGER.error("Failed to write dimensions to file", e);
        }
    }
}