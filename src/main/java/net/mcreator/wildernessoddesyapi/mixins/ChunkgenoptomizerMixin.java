package net.mcreator.wildernessoddesyapi.mixins;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.world.level.chunk;
import net.minecraft.world.level.chunk.storage;
import net.minecraft.world.level.ChunkPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;
import java.util.Map;

@Mixin(ChunkManager.class)
public abstract class ChunkgenoptomizerMixin {

    private static final Map<ChunkPos, Chunk> chunkCache = new HashMap<>();

    @Inject(method = "loadChunk", at = @At("HEAD"), cancellable = true)
    private void onLoadChunk(ChunkPos chunkPos, boolean create, CallbackInfoReturnable<Chunk> cir) {
        // Check the cache first
        if (chunkCache.containsKey(chunkPos)) {
            cir.setReturnValue(chunkCache.get(chunkPos));
            System.out.println("Chunk loaded from cache: " + chunkPos);
            return;
        }

        // Proceed with the original method
        // When the chunk is loaded, add it to the cache
        Chunk chunk = cir.getReturnValue();
        if (chunk != null) {
            chunkCache.put(chunkPos, chunk);
            System.out.println("Chunk added to cache: " + chunkPos);
        }

        System.out.println("Optimized chunk loading logic executed for chunk: " + chunkPos);
    }
}