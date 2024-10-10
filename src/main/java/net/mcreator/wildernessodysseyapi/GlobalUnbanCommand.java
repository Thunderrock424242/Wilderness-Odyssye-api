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
package net.mcreator.wildernessodysseyapi.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.mcreator.wildernessodysseyapi.GlobalBanManager;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class GlobalUnbanCommand {

    private static final String AUTHORIZED_ADMIN_UUID = "your-unique-player-uuid";

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("globalunban")
                .requires(source -> source.getEntity() instanceof ServerPlayer &&
                        ((ServerPlayer) source.getEntity()).getStringUUID().equals(AUTHORIZED_ADMIN_UUID))
                .then(Commands.argument("playerUUID", StringArgumentType.word())
                        .executes(context -> {
                            String playerUUID = StringArgumentType.getString(context, "playerUUID");

                            GlobalBanManager.unbanPlayerGlobally(playerUUID);

                            context.getSource().sendSuccess(() -> Component.literal("Player " + playerUUID + " has been globally unbanned."), true);
                            return 1;
                        })));
    }
}
