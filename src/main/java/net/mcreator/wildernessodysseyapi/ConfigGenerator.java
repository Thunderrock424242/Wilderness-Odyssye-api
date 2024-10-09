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
*/
package net.mcreator.wildernessodysseyapi;

import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;

public class ConfigGenerator {

    private static final ModConfigSpec.Builder COMMON_BUILDER = new ModConfigSpec.Builder();

    // Configuration entries
    public static final ModConfigSpec.BooleanValue AGREE_TO_TERMS;
    public static final ModConfigSpec.BooleanValue ANTI_CHEAT_ENABLED;
    public static final ModConfigSpec.BooleanValue GLOBAL_BAN_ENABLED;
    public static final ModConfigSpec.BooleanValue GLOBAL_LOGGING_ENABLED;

    static {
        COMMON_BUILDER.comment("Wilderness Oddessy API Anti-Cheat Configuration").push("anti_cheat");

        // Agreement to Terms and Privacy Policy
        AGREE_TO_TERMS = COMMON_BUILDER
                .comment("You must agree to the terms and privacy policy outlined in the README.md file to use this mod.",
                        "Set this to true to confirm your agreement. The server will not start unless this is set to true.")
                .define("agreeToTerms", false);

        // Enable or disable anti-cheat features (only applicable if the server is whitelisted)
        ANTI_CHEAT_ENABLED = COMMON_BUILDER
                .comment("Enable or disable the anti-cheat functionality. Note: Anti-cheat will only be active if the server is on the hardcoded whitelist.")
                .define("antiCheatEnabled", true);

        // Enable or disable global banning
        GLOBAL_BAN_ENABLED = COMMON_BUILDER
                .comment("Enable or disable global banning. Default is false for data privacy reasons.")
                .define("globalBanEnabled", false);

        // Enable or disable global logging for player violations
        GLOBAL_LOGGING_ENABLED = COMMON_BUILDER
                .comment("Enable or disable global logging for player violations.")
                .define("globalLoggingEnabled", true);

        COMMON_BUILDER.pop();
    }

    public static final ModConfigSpec COMMON_CONFIG = COMMON_BUILDER.build();

    public static void register() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, COMMON_CONFIG);
    }
}
