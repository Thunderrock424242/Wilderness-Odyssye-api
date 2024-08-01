package net.mcreator.wildernessoddesyapi.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.scores.PlayerTeam;

import java.util.UUID;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

public class SeePlayersCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        LiteralArgumentBuilder<CommandSourceStack> builder = Commands.literal("seeplayers")
            .requires(source -> source.hasPermission(2)) // Requires op level 2
            .executes(context -> showPlayers(context.getSource()));

        builder.then(Commands.literal("spectate")
            .then(Commands.argument("playerId", StringArgumentType.string())
                .executes(context -> spectatePlayer(context.getSource(), StringArgumentType.getString(context, "playerId")))));

        dispatcher.register(builder);
    }

    private static int spectatePlayer(CommandSourceStack source, String playerId) {
        ServerPlayer targetPlayer = source.getServer().getPlayerList().getPlayer(UUID.fromString(playerId));
        if (targetPlayer != null) {
            try {
                ServerPlayer player = source.getPlayerOrException();
                player.setCamera(targetPlayer);
                source.sendSuccess(() -> Component.literal("You are now spectating " + targetPlayer.getName().getString()), false);
            } catch (CommandSyntaxException e) {
                source.sendFailure(Component.literal("Error: " + e.getMessage()));
            }
        } else {
            source.sendFailure(Component.literal("Player with ID " + playerId + " not found."));
        }
        return 1;
    }

    private static void makePlayersVisible(ServerPlayer player) {
        PlayerTeam team = new PlayerTeam(player.getScoreboard(), "visiblePlayers");
        team.setNameTagVisibility(PlayerTeam.Visibility.ALWAYS);
        team.setSeeFriendlyInvisibles(true); // This is a workaround to make players outline visible

        for (ServerPlayer otherPlayer : player.getServer().getPlayerList().getPlayers()) {
            player.getScoreboard().addPlayerToTeam(otherPlayer.getName().getString(), team);
        }
    }

    private static int showPlayers(CommandSourceStack source) {
        for (ServerPlayer player : source.getServer().getPlayerList().getPlayers()) {
            source.sendSuccess(() -> Component.literal("Player: " + player.getName().getString() + " ID: " + player.getUUID().toString()), false);
            makePlayersVisible(player);
        }
        return 1;
    }
}
