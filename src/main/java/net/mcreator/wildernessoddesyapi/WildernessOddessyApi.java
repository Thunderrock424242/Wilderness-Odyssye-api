package net.mcreator.wildernessoddesyapi;

import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.bus.api.SubscribeEvent;

import net.mcreator.wildernessoddesyapi.command.ClearItemsCommand;
import net.mcreator.wildernessoddesyapi.command.SeePlayersCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(WildernessOddessyApi.MOD_ID)
public class WildernessOddessyApi {
    public static final String MOD_ID = "wilderness_oddesy_api";
    private static final Logger LOGGER = LogManager.getLogger();
    public static boolean ENABLE_OUTLINE = true; // Default is false meant to be used in dev environment.

    public WildernessOddessyApi() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
        NeoForge.EVENT_BUS.register(this);
        new MobStages();
        new DimensionLogger();
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("Common setup complete");
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        LOGGER.info("Client setup complete");
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        ClearItemsCommand.register(event.getServer().getCommands().getDispatcher());
        SeePlayersCommand.register(event.getServer().getCommands().getDispatcher());
        LOGGER.info("Server starting setup complete");
    }
}
