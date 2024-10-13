/*
 * The code of this mod element is always locked.
 *
 * You can register new events in this class too.
 *
 * If you want to make a plain independent class, create it using
 * Project Browser -> New... and make sure to make the class
 * outside net.mcreator.wildernessodysseyapi as this package is managed by MCreator.
 *
 * If you change workspace package, modid or prefix, you will need
 * to manually adapt this file to these changes or remake it.
 *
 * This class will be added in the mod root package.
*/
package net.mcreator.wildernessodysseyapi;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.network.chat.Component;

public class DimensionTPCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("tpdim")
                .then(Commands.argument("dimension", ResourceLocationArgument.id())
                .executes(context -> {
                    String dim = ResourceLocationArgument.getId(context, "dimension").toString();
                    ServerLevel targetWorld = getDimensionByName(dim, context.getSource());
                    if (targetWorld != null) {
                        Player player = context.getSource().getPlayerOrException();
                        player.teleportTo(targetWorld, player.getX(), player.getY(), player.getZ(), player.getYRot(), player.getXRot());
                        player.displayClientMessage(Component.literal("Teleported to dimension: " + dim), true);
                        return 1;
                    } else {
                        context.getSource().sendFailure(Component.literal("Invalid dimension!"));
                        return 0;
                    }
                })));
    }

    private static ServerLevel getDimensionByName(String dimName, CommandSourceStack source) {
        ResourceKey<Level> targetDimension = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(dimName));
        return source.getServer().getLevel(targetDimension);
    }
}
