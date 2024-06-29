/*
 * The code of this mod element is always locked.
 *
 * You can register new events in this class too.
 *
 * If you want to make a plain independent class, create it using
 * Project Browser -> New... and make sure to make the class
 * outside net.mcreator.wildernessoddesyapi as this package is managed by MCreator.
 *
 * If you change workspace package, modid or prefix, you will need
 * to manually adapt this file to these changes or remake it.
 *
 * This class will be added in the mod root package.
/*package net.mcreator.wildernessoddesyapi.mixins;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class MixinFailCheckMixin implements IMixinConfigPlugin {

    private static final Logger LOGGER = LogManager.getLogger(MixinFailCheckMixin.class);

    @Override
    public void onLoad(String mixinPackage) {
        // No action needed on load
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
        // No action needed
    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
        // No action needed before applying mixin
    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
        if (!targetClassName.equals(mixinClassName)) {
            LOGGER.fatal("Mixin application failed silently for: " + mixinClassName);
        } else {
            LOGGER.warn("Mixin applied successfully: " + mixinClassName);
        }
    }
}
add in the mixins file under the package  "plugin": "net.mcreator.wildernessoddesyapi.mixins.MixinFailCheckMixin", when there is a feature to overide base files.
*/
