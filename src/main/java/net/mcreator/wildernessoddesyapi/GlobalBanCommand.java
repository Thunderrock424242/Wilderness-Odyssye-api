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
package net.mcreator.wildernessoddesyapi.command;

import net.mcreator.wildernessoddesyapi.GlobalBanManager;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class GlobalBanCommand {

    private static final String AUTHORIZED_ADMIN_UUID = "your-unique-player-uuid";

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("globalban")
                .requires(source -> source.getEntity() instanceof ServerPlayer &&
                        ((ServerPlayer) source.getEntity()).getStringUUID().equals(AUTHORIZED_ADMIN_UUID))
                .then(Commands.argument("playerUUID", StringArgumentType.word())
                        .then(Commands.argument("reason", StringArgumentType.greedyString())
                                .executes(context -> {
                                    String playerUUID = StringArgumentType.getString(context, "playerUUID");
                                    String reason = StringArgumentType.getString(context, "reason");

                                    GlobalBanManager.banPlayerGlobally(playerUUID, reason);

                                    context.getSource().sendSuccess(() -> Component.literal("Player " + playerUUID + " has been globally banned for: " + reason), true);
                                    return 1;
                                }))));
    }
}
