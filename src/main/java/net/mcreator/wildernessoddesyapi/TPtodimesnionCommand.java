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


import org.checkerframework.checker.units.qual.s;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.common.util.FakePlayerFactory;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.Direction;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.Commands;

import net.mcreator.TptodimesnionCommand.custom.TPProcedure;

import com.mojang.brigadier.arguments.StringArgumentType;


@Mod.EventBusSubscriber
public class TPtodimesnionCommand {
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