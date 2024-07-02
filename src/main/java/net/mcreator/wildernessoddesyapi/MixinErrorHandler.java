package net.mcreator.wildernessoddesyapi;

import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.IEventBus;

@Mod("wilderness_oddesy_api")
public class MixinErrorHandler {
    public static final String MODID = "wilderness_oddesy_api";

    public MixinErrorHandler() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.register(this);
    }

    @SubscribeEvent
    public void setup(FMLCommonSetupEvent event) {
        MixinErrorLogger.logInfo("Mixin Error Logger Mod Initialized");
    }
}
