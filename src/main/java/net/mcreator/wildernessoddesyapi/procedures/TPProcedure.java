package net.mcreator.wildernessoddesyapi.procedures;

import net.minecraft.core.Vec3i;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.CommandSource;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.arguments.StringArgumentType;

import net.minecraft.commands.arguments.DimensionArgument;

public class TPProcedure {
    public static void execute(LevelAccessor world, CommandContext<CommandSourceStack> arguments) {
        try {
            Vec3i pos = BlockPosArgument.getLoadedBlockPos(arguments, "destination");
        if (world instanceof ServerLevel _level)
            _level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(pos.getX(),pos.getY(),pos.getZ()), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
                    ("execute in " + DimensionArgument.getDimension(arguments, "dimension_id").dimension().location().toString() + " run tp " + EntityArgument.getEntity(arguments, "target_entity").getDisplayName().getString() + " ~ ~ ~"));
        } catch (CommandSyntaxException e) {
            e.printStackTrace();
        }
    }
}