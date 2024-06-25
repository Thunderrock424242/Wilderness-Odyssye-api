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

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod("wilderness_oddesy_api")
public class WorldLoadingScreen {
    public WorldLoadingScreen() {
        NeoForge.EVENT_BUS.register(this);
        NeoForge.EVENT_BUS.addListener(this::setup);
        NeoForge.EVENT_BUS.addListener(this::doClientStuff);
    }

    private void setup(final FMLCommonSetupEvent event) {
        // Initialization logic
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        // Client setup logic
    }

    @SubscribeEvent
    public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        // Show the custom loading screen
        Minecraft.getInstance().execute(() -> Minecraft.getInstance().setScreen(new LoadingScreen()));
    }

    @OnlyIn(Dist.CLIENT)
    public static class LoadingScreen extends Screen {
        private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation("yourmodid", "textures/gui/loading_screen.png");
        private final long startTime;
        private final long duration = 30000; // 30 seconds

        public LoadingScreen() {
            super(Component.literal("Loading..."));
            this.startTime = System.currentTimeMillis();
        }

        @Override
        public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
            this.renderBackground(guiGraphics, mouseX, mouseY, partialTicks);
            RenderSystem.setShaderTexture(0, BACKGROUND_TEXTURE);
            guiGraphics.blit(BACKGROUND_TEXTURE, this.width / 2 - 128, this.height / 2 - 128, 0, 0, 256, 256, 256, 256);

            // Draw the message
            guiGraphics.drawCenteredString(this.font, "Please wait for the world to generate.", this.width / 2, this.height / 2 - 30, 0xFFFFFF);
            guiGraphics.drawCenteredString(this.font, "This may take 30 seconds to load.", this.width / 2, this.height / 2 - 10, 0xFFFFFF);
            guiGraphics.drawCenteredString(this.font, "This is so you have the perfect experience.", this.width / 2, this.height / 2 + 10, 0xFFFFFF);

            // Draw the loading bar
            long elapsedTime = System.currentTimeMillis() - startTime;
            int barWidth = (int)((elapsedTime / (float) duration) * 200);
            guiGraphics.fill(this.width / 2 - 100, this.height / 2 + 30, this.width / 2 - 100 + barWidth, this.height / 2 + 50, 0xFF00FF00);

            super.render(guiGraphics, mouseX, mouseY, partialTicks);

            if (elapsedTime > duration) {
                Minecraft.getInstance().setScreen(null); // Close the screen after 30 seconds
            }
        }

        @Override
        public boolean isPauseScreen() {
            return false;
        }
    }
}
