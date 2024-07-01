package net.mcreator.wildernessoddesyapi;

import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.eventbus.api.EventBus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.extensibility.IMixinErrorHandler;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.spongepowered.asm.mixin.extensibility.IMixinConfig;
import org.spongepowered.asm.mixin.throwables.MixinApplyError;

@Mod(MixinErrorLogger.MODID)
public class MixinErrorLogger implements IMixinErrorHandler {
    public static final String MODID = "mixinerrorlogger";
    public static final Logger LOGGER = LogManager.getLogger();

    public MixinErrorLogger() {
        net.neoforged.api.eventbus.api.EventBus eventBus = new net.neoforged.api.eventbus.api.EventBus();
        eventBus.register(this);
        initMixinErrorHandler();
    }

    @SubscribeEvent
    public void setup(FMLCommonSetupEvent event) {
        LOGGER.info("Mixin Error Logger Mod Initialized");
    }

    private void initMixinErrorHandler() {
        MixinEnvironment.getDefaultEnvironment().registerErrorHandler(this);
    }

    @Override
    public ErrorAction onPrepareError(IMixinConfig config, Throwable th, IMixinInfo mixin, ErrorAction action) {
        LOGGER.error("Mixin prepare error: {}", th.getMessage());
        LOGGER.error("Mixin config: {}", config.getName());
        LOGGER.error("Mixin: {}", mixin.getName());
        LOGGER.error("Cause: ", th);
        return ErrorAction.ERROR;
    }

    @Override
    public ErrorAction onApplyError(String targetClassName, Throwable targetError, IMixinInfo mixin, ErrorAction action) {
        LOGGER.error("Mixin apply error: {}", targetError.getMessage());
        LOGGER.error("Target class: {}", targetClassName);
        LOGGER.error("Mixin: {}", mixin.getName());
        LOGGER.error("Cause: ", targetError);
        return ErrorAction.ERROR;
    }

    @Override
    public ErrorAction onError(String mixinClassName, Throwable error, IMixinInfo mixin, ErrorAction action) {
        LOGGER.error("Mixin error: {}", error.getMessage());
        LOGGER.error("Mixin class: {}", mixinClassName);
        LOGGER.error("Mixin: {}", mixin.getName());
        LOGGER.error("Cause: ", error);
        return ErrorAction.ERROR;
    }
}
