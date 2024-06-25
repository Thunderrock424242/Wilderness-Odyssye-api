package net.mcreator.wildernessoddesyapi;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
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
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.minecraft.commands.arguments.EntityArgument;

@Mod("wilderness_oddesy_api")
public class TPdimension {
    public static final String MODID = "wilderness_oddesy_api";

    public TPdimension() {
        net.neoforged.neoforge.common.NeoForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void setup(final FMLCommonSetupEvent event) {
        // Setup tasks
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        CommandRegistration.register(event.getServer().getCommands().getDispatcher());
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
        ResourceKey<Level> dimensionKey = ResourceKey.create(ResourceKey.createRegistryKey(new ResourceLocation("dimension")), new ResourceLocation(dimension));
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
