package net.mcreator.wildernessoddesyapi;

import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.level.ChunkEvent;

public class ChunkEventHandler {

    @SubscribeEvent
    public void onChunkLoad(ChunkEvent.Load event) {
        ChunkAccess chunk = event.getChunk();
        if (chunk instanceof LevelChunk levelChunk) {
            ChunkDiagnostics.checkChunkForIssues(levelChunk);
        }
    }

    @SubscribeEvent
    public void onChunkUnload(ChunkEvent.Unload event) {
        // Handle chunk unload events
        System.out.println("Chunk unloaded: " + event.getChunk().getPos());
    }
}
