package net.mcreator.wildernessoddesyapi;

import net.mcreator.wildernessoddesyapi.command.ClearItemsCommand;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.bus.api.SubscribeEvent;

@Mod("wilderness_oddesy_api")
public class WildernessOddessyApi {
    public static final String MOD_ID = "wilderness_oddesy_api";

    public WildernessOddessyApi() {
        // Register the setup methods for different mod loading stages
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);

        // Register other events (e.g., item registration)
        NeoForge.EVENT_BUS.register(this);

        // Initialize the MobStages to set up configuration
        new MobStages();
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Common setup code
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        // Client-only setup code
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        ClearItemsCommand.register(event.getServer().getCommands().getDispatcher());
    }
}
