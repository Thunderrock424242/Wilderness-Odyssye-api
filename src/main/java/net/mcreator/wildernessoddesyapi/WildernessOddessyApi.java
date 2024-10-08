package net.mcreator.wildernessoddesyapi;

import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/// don't forget to add command imports.
import net.mcreator.wildernessoddesyapi.command.GlobalUnbanCommand;
import net.mcreator.wildernessoddesyapi.command.GlobalBanCommand;
import net.mcreator.wildernessoddesyapi.command.ClearItemsCommand;
import net.mcreator.wildernessoddesyapi.command.AdminCommand;


import java.util.Set;

@Mod(WildernessOddessyApi.MOD_ID)
public class WildernessOddessyApi {

    public static final String MOD_ID = "wilderness_oddesy_api";
    private static final Logger LOGGER = LogManager.getLogger();
     public static boolean ENABLE_OUTLINE = true; // Default is false meant to be used in dev environment.

    // Hardcoded Server Whitelist - Only these servers can use the anti-cheat feature
    private static final Set<String> SERVER_WHITELIST = Set.of(
            "server-id-1",
            "server-id-2",
            "server-id-3"
    );

    // Configuration flags
    public static boolean antiCheatEnabled;
    public static boolean globalLoggingEnabled;
    public static boolean globalBanEnabled;

    public WildernessOddessyApi(IEventBus modBus) {
        // Register mod lifecycle events on the mod event bus
        modBus.addListener(this::commonSetup);
        modBus.addListener(this::clientSetup);
        modBus.addListener(this::onLoadComplete);

        // Register server events
        NeoForge.EVENT_BUS.register(this);
        NeoForge.EVENT_BUS.register(ModWhitelistChecker.class);

        // Register configuration
        ConfigGenerator.register();

        // Load config settings
        loadConfig();

        // If terms are not agreed to, terminate server startup
        if (!ConfigGenerator.AGREE_TO_TERMS.get()) {
            LOGGER.fatal("You must agree to the terms outlined in the README.md file by setting 'agreeToTerms' to true in the configuration file.");
            throw new RuntimeException("Server cannot start without agreement to the mod's terms and conditions.");
        }

        // Enable anti-cheat only if the server is whitelisted
        String currentServerId = "server-unique-id";  // Replace with the logic to fetch the current server's unique ID
        antiCheatEnabled = SERVER_WHITELIST.contains(currentServerId);

        // Generate README file during initialization
        READMEGenerator.generateReadme();

        LOGGER.info("Wilderness Oddessy Anti-Cheat Mod Initialized. Anti-cheat enabled: " + antiCheatEnabled);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("Common setup complete");
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        LOGGER.info("Client setup complete");
    }

    private void onLoadComplete(final FMLLoadCompleteEvent event) {
        LOGGER.info("Load complete");
    }

    @SubscribeEvent
public void onServerStarting(ServerStartingEvent event) {
    // Register anti-cheat commands during server start, if enabled
    if (globalBanEnabled) {
        GlobalBanCommand.register(event.getServer().getCommands().getDispatcher());
        GlobalUnbanCommand.register(event.getServer().getCommands().getDispatcher());
        LOGGER.info("Global ban commands registered");
    }

    // Register regular commands (non-anti-cheat related)
    ClearItemsCommand.register(event.getServer().getCommands().getDispatcher());
    AdminCommand.register(event.getServer().getCommands().getDispatcher());
    LOGGER.info("Regular commands registered");

    LOGGER.info("Server starting setup complete. Anti-cheat enabled: " + antiCheatEnabled);
}

    private void loadConfig() {
        // Load settings from configuration
        globalLoggingEnabled = ConfigGenerator.GLOBAL_LOGGING_ENABLED.get();
        globalBanEnabled = ConfigGenerator.GLOBAL_BAN_ENABLED.get();
    }

    public static boolean isGlobalLoggingEnabled() {
        return globalLoggingEnabled;
    }
}
