package net.mcreator.wildernessodysseyapi;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@EventBusSubscriber(modid = "yourmodid")
public class EntityOutlineRenderer {

    private static final double CHUNK_RADIUS = 30 * 16; // 30 chunks radius
    private static final Set<UUID> playersWithGlowingEffect = new HashSet<>();

    public static void addPlayerWithGlowingEffect(UUID playerId) {
        playersWithGlowingEffect.add(playerId);
    }

    public static void removePlayerWithGlowingEffect(UUID playerId) {
        playersWithGlowingEffect.remove(playerId);
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onRenderWorldLast(RenderLevelStageEvent event) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;

        if (player != null && playersWithGlowingEffect.contains(player.getUUID())) {
            Vec3 playerPos = player.position();
            AABB boundingBox = new AABB(playerPos.x - CHUNK_RADIUS, playerPos.y - CHUNK_RADIUS, playerPos.z - CHUNK_RADIUS,
                    playerPos.x + CHUNK_RADIUS, playerPos.y + CHUNK_RADIUS, playerPos.z + CHUNK_RADIUS);
            for (Entity entity : mc.level.getEntities(null, boundingBox)) {
                if (entity instanceof Player && !entity.equals(player)) { // Only render other players
                    outlineEntity(entity, event.getPartialTick());
                }
            }
        }
    }

    private static void outlineEntity(Entity entity, float partialTicks) {
        Minecraft mc = Minecraft.getInstance();
        EntityRenderer<? super Entity> renderer = mc.getEntityRenderDispatcher().getRenderer(entity);
        if (renderer != null) {
            PoseStack poseStack = new PoseStack();
            MultiBufferSource.BufferSource bufferSource = mc.renderBuffers().bufferSource();
            renderer.render(entity, entity.getYRot(), partialTicks, poseStack, bufferSource, mc.getEntityRenderDispatcher().getPackedLightCoords(entity, partialTicks));
            bufferSource.endBatch(RenderType.outline(renderer.getTextureLocation(entity)));
        }
    }
}
