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
package net.mcreator.wildernessoddesyapi;


import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.arguments.EntityArgument;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("tptodimension")
public class TPdimension {
    public static final String MODID = "tptodimension";

    public TPdimension() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
    }

    private void setup(final FMLCommonSetupEvent event) {
        CommandRegistration.register(event.getCommandDispatcher());
    }
}

class CommandRegistration {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("tptodimension")
            .then(Commands.argument("player", EntityArgument.player())
                .then(Commands.argument("dimension", StringArgumentType.string())
                    .then(Commands.argument("x", DoubleArgumentType.doubleArg())
                        .then(Commands.argument("y", DoubleArgumentType.doubleArg())
                            .then(Commands.argument("z", DoubleArgumentType.doubleArg())
                                .executes(context -> execute(context,
                                    EntityArgument.getPlayer(context, "player"),
                                    StringArgumentType.getString(context, "dimension"),
                                    DoubleArgumentType.getDouble(context, "x"),
                                    DoubleArgumentType.getDouble(context, "y"),
                                    DoubleArgumentType.getDouble(context, "z")))))))));
    }

    private static int execute(CommandContext<CommandSourceStack> context, ServerPlayer player, String dimension, double x, double y, double z) {
        MinecraftServer server = context.getSource().getServer();
        ResourceKey<Level> dimensionKey = ResourceKey.create(Level.DIMENSION, new ResourceLocation(dimension));
        ServerLevel targetWorld = server.getLevel(dimensionKey);

        if (targetWorld != null) {
            player.teleportTo(targetWorld, x, y, z, player.getYRot(), player.getXRot());
            context.getSource().sendSuccess(() -> Component.literal("Teleported " + player.getName().getString() + " to " + x + ", " + y + ", " + z + " in dimension " + dimension), true);
            return 1;
        } else {
            context.getSource().sendFailure(Component.literal("Dimension " + dimension + " not found."));
            return 0;
        }
    }
}
