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
package net.mcreator.wildernessodysseyapi.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.network.chat.Component;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.bus.api.SubscribeEvent;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.event.RegisterCommandsEvent;

@EventBusSubscriber(modid = "yourmodid")
public class DimensionTPCommand {

    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        dispatcher.register(Commands.literal("tpdim")
                .then(Commands.argument("dimension", ResourceLocationArgument.id())
                        .suggests((context, builder) -> {
                            // Auto-suggest available dimensions
                            return net.minecraft.commands.SharedSuggestionProvider.suggestResource(
                                    context.getSource().getServer().registryAccess().registryOrThrow(Registries.DIMENSION).keySet(), builder);
                        })
                        .executes(context -> {
                            ResourceLocation dimensionLocation = ResourceLocationArgument.getId(context, "dimension");
                            ServerLevel targetWorld = getDimensionByName(dimensionLocation, context.getSource().getServer());

                            if (targetWorld != null) {
                                Player player = context.getSource().getPlayerOrException();
                                player.teleportTo(targetWorld, player.getX(), player.getY(), player.getZ(), player.getYRot(), player.getXRot());
                                player.displayClientMessage(Component.literal("Teleported to dimension: " + dimensionLocation.toString()), true);
                                return 1;
                            } else {
                                context.getSource().sendFailure(Component.literal("Invalid dimension!"));
                                return 0;
                            }
                        })
                )
        );
    }

    private static ServerLevel getDimensionByName(ResourceLocation dimensionLocation, MinecraftServer server) {
        ResourceKey<ServerLevel> targetDimension = ResourceKey.create(Registries.DIMENSION, dimensionLocation);
        return server.getLevel(targetDimension);
    }
}
