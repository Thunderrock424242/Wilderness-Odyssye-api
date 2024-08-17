package net.mcreator.wildernessoddesyapi.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class StructureTPCommand {

    private static final SuggestionProvider<CommandSourceStack> STRUCTURE_SUGGESTIONS = (context, builder) -> {
        return suggestStructures(context, builder);
    };

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("structuretp")
            .then(Commands.argument("structure", StringArgumentType.word())
                .suggests(STRUCTURE_SUGGESTIONS)
                .executes(context -> {
                    return execute(context);
                })
            )
        );
    }

    private static int execute(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayerOrException();
        String structureName = StringArgumentType.getString(context, "structure");

        ResourceLocation structureKey = new ResourceLocation(structureName);
        Structure structure = context.getSource().getLevel().registryAccess().registryOrThrow(Registries.STRUCTURE).get(structureKey);

        if (structure == null) {
            context.getSource().sendFailure(Component.literal("Structure not found."));
            return Command.SINGLE_SUCCESS;
        }

        ServerLevel level = context.getSource().getLevel();
        BlockPos playerPos = player.blockPosition();

        Optional<BlockPos> structurePos = level.findNearestMapFeature(structure, playerPos, 1000, false);

        if (!structurePos.isPresent()) {
            context.getSource().sendFailure(Component.literal("Structure not found within 1000 blocks."));
            return Command.SINGLE_SUCCESS;
        }

        teleportPlayer(player, structurePos.get());
        context.getSource().sendSuccess(() -> Component.literal("Teleported to " + structureName + "."), false);
        return Command.SINGLE_SUCCESS;
    }

    private static CompletableFuture<Suggestions> suggestStructures(CommandContext<CommandSourceStack> context, SuggestionsBuilder builder) {
        context.getSource().getLevel().registryAccess().registryOrThrow(Registries.STRUCTURE).keySet().forEach(key -> builder.suggest(key.toString()));
        return builder.buildFuture();
    }

    private static void teleportPlayer(Entity entity, BlockPos pos) {
        Vec3 destination = Vec3.atBottomCenterOf(pos);
        entity.teleportTo(destination.x, destination.y, destination.z);
    }
}
