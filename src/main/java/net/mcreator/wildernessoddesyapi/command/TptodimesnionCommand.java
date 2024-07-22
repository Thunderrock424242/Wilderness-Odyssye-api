

import org.checkerframework.checker.units.qual.s;

import net.neoforged.fml.common.Mod;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.common.util.FakePlayerFactory;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.Direction;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.Commands;

import net.mcreator.wildernessoddesyapi.procedures.TPProcedure;

import com.mojang.brigadier.arguments.StringArgumentType;

@EventBusSubscriber
public class TptodimesnionCommand {
    @SubscribeEvent
    public static void registerCommand(RegisterCommandsEvent event) {
        event.getDispatcher().register(Commands.literal("tptodimesnion").requires(s -> s.hasPermission(4))
                .then(Commands.argument("target_entity", EntityArgument.player()).then(Commands.argument("destination", BlockPosArgument.blockPos()).then(Commands.argument("dimension_id", StringArgumentType.string()).executes(arguments -> {
                    Level world = arguments.getSource().getUnsidedLevel();
                    double x = arguments.getSource().getPosition().x();
                    double y = arguments.getSource().getPosition().y();
                    double z = arguments.getSource().getPosition().z();
                    Entity entity = arguments.getSource().getEntity();
                    if (entity == null && world instanceof ServerLevel _servLevel)
                        entity = FakePlayerFactory.getMinecraft(_servLevel);
                    Direction direction = Direction.DOWN;
                    if (entity != null)
                        direction = entity.getDirection();

                    TPProcedure.execute(world, arguments);
                    return 0;
                })))));
    }
}