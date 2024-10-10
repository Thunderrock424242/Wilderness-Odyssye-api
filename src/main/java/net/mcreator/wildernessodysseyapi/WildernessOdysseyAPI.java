package net.mcreator.wildernessodysseyapi;

import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.bus.api.IEventBus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.mcreator.wildernessodysseyapi.command.GlobalUnbanCommand;
import net.mcreator.wildernessodysseyapi.command.GlobalBanCommand;
import net.mcreator.wildernessodysseyapi.command.ClearItemsCommand;
import net.mcreator.wildernessodysseyapi.command.AdminCommand;

import java.util.Set;

@Mod(WildernessOdysseyAPI.MOD_ID)
public class WildernessOdysseyAPI {
    public static final String MOD_ID = "wilderness_oddesy_api";
    private static final Logger LOGGER = LogManager.getLogger();
    public static boolean ENABLE_OUTLINE = true; // Default is false meant to be used in dev environment.
    private static final Set<String> SERVER_WHITELIST = Set.of
            ("server-id-1", "server-id-2", "server-id-3");

    public static boolean antiCheatEnabled;
    public static boolean globalLoggingEnabled;
    public static boolean globalBanEnabled;

    public WildernessOdysseyAPI(IEventBus modBus, ModContainer container) {
        modBus.addListener(this::commonSetup);
        modBus.addListener(this::clientSetup);
        modBus.addListener(this::onLoadComplete);

        NeoForge.EVENT_BUS.register(this);
        NeoForge.EVENT_BUS.register(ModWhitelistChecker.class);

        ConfigGenerator.register();
        loadConfig();

        if (!ConfigGenerator.AGREE_TO_TERMS.get()) {
            LOGGER.fatal("Agree to the terms by setting 'agreeToTerms' to true.");
            throw new RuntimeException("Server cannot start without agreement.");
        }

        String currentServerId = "server-unique-id"; // Fetch server ID logic here
        antiCheatEnabled = SERVER_WHITELIST.contains(currentServerId);

        READMEGenerator.generateReadme();
        LOGGER.info("Wilderness Odyssey Mod Initialized. Anti-cheat: " + antiCheatEnabled);
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
        if (globalBanEnabled) {
            GlobalBanCommand.register(event.getServer().getCommands().getDispatcher());
            GlobalUnbanCommand.register(event.getServer().getCommands().getDispatcher());
        }
        ClearItemsCommand.register(event.getServer().getCommands().getDispatcher());
        AdminCommand.register(event.getServer().getCommands().getDispatcher());
        LOGGER.info("Server starting setup complete. Anti-cheat: " + antiCheatEnabled);
    }

    private void loadConfig() {
        globalLoggingEnabled = ConfigGenerator.GLOBAL_LOGGING_ENABLED.get();
        globalBanEnabled = ConfigGenerator.GLOBAL_BAN_ENABLED.get();
    }
}
