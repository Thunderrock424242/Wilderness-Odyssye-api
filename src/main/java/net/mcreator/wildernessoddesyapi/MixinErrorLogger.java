package net.mcreator.wildernessoddesyapi;

import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.IEventBus;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.extensibility.IMixinErrorHandler;

@Mod(MixinErrorLogger.MODID)
public class MixinErrorLogger {
    public static final String MODID = "mixinerrorlogger";
    public static final Logger LOGGER = LogManager.getLogger();

    public MixinErrorLogger() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.register(this);
        initMixinErrorHandler();
    }

    private void initMixinErrorHandler() {
        // Programmatically set the error handler if necessary, or set it via the mixin config JSON
        MixinEnvironment.getDefaultEnvironment().registerErrorHandlerClass(MixinErrorHandler.class.getName());
    }

    @SubscribeEvent
    public void setup(FMLCommonSetupEvent event) {
        LOGGER.info("Mixin Error Logger Mod Initialized");
    }
}
