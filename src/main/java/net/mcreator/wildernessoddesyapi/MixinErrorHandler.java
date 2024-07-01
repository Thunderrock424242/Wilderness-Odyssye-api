package net.mcreator.wildernessoddesyapi;

import org.spongepowered.asm.mixin.extensibility.IMixinConfig;
import org.spongepowered.asm.mixin.extensibility.IMixinErrorHandler;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.spongepowered.asm.mixin.throwables.MixinApplyError;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class MixinErrorHandler implements IMixinErrorHandler {
    private static final Logger LOGGER = LogManager.getLogger(MixinErrorHandler.class);

    @Override
    public ErrorAction onPrepareError(IMixinConfig config, Throwable th, IMixinInfo mixinInfo, ErrorAction action) {
        LOGGER.error("Mixin prepare error: {}", th.getMessage());
        LOGGER.error("Mixin config: {}", config.getName());
        LOGGER.error("Mixin: {}", mixinInfo.getName());
        LOGGER.error("Cause: ", th);
        return ErrorAction.ERROR;
    }

    @Override
    public ErrorAction onApplyError(String targetClassName, Throwable targetError, IMixinInfo mixinInfo, ErrorAction action) {
        LOGGER.error("Mixin apply error: {}", targetError.getMessage());
        LOGGER.error("Target class: {}", targetClassName);
        LOGGER.error("Mixin: {}", mixinInfo.getName());
        LOGGER.error("Cause: ", targetError);
        return ErrorAction.ERROR;
    }

    @Override
    public ErrorAction onError(String mixinClassName, Throwable th, IMixinInfo mixinInfo, ErrorAction action) {
        LOGGER.error("Mixin error: {}", th.getMessage());
        LOGGER.error("Mixin class: {}", mixinClassName);
        LOGGER.error("Mixin: {}", mixinInfo.getName());
        LOGGER.error("Cause: ", th);
        return ErrorAction.ERROR;
    }
}
