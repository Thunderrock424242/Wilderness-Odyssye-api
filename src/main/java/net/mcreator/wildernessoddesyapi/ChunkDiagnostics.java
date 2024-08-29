package net.mcreator.wildernessoddesyapi;

import net.minecraft.world.level.chunk.LevelChunk;

public class ChunkDiagnostics {

    public static void checkChunkForIssues(LevelChunk chunk) {
        // Corrected method to check for block entities
        if (chunk.getBlockEntities().isEmpty()) {
            System.out.println("Warning: Chunk at " + chunk.getPos() + " has no block entities.");
        }

        // Additional checks can be added here
    }
}