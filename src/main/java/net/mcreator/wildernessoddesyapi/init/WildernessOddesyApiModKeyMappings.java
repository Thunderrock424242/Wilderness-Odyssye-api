
/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.wildernessoddesyapi.init;

import org.lwjgl.glfw.GLFW;

import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.event.TickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.Dist;

import net.minecraft.client.Minecraft;
import net.minecraft.client.KeyMapping;

import net.mcreator.wildernessoddesyapi.network.CrawlkeyMessage;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = {Dist.CLIENT})
public class WildernessOddesyApiModKeyMappings {
	public static final KeyMapping CRAWLKEY = new KeyMapping("key.wilderness_oddesy_api.crawlkey", GLFW.GLFW_KEY_C, "key.categories.movement") {
		private boolean isDownOld = false;

		@Override
		public void setDown(boolean isDown) {
			super.setDown(isDown);
			if (isDownOld != isDown && isDown) {
				PacketDistributor.SERVER.noArg().send(new CrawlkeyMessage(0, 0));
				CrawlkeyMessage.pressAction(Minecraft.getInstance().player, 0, 0);
				CRAWLKEY_LASTPRESS = System.currentTimeMillis();
			} else if (isDownOld != isDown && !isDown) {
				int dt = (int) (System.currentTimeMillis() - CRAWLKEY_LASTPRESS);
				PacketDistributor.SERVER.noArg().send(new CrawlkeyMessage(1, dt));
				CrawlkeyMessage.pressAction(Minecraft.getInstance().player, 1, dt);
			}
			isDownOld = isDown;
		}
	};
	private static long CRAWLKEY_LASTPRESS = 0;

	@SubscribeEvent
	public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
		event.register(CRAWLKEY);
	}

	@Mod.EventBusSubscriber({Dist.CLIENT})
	public static class KeyEventListener {
		@SubscribeEvent
		public static void onClientTick(TickEvent.ClientTickEvent event) {
			if (Minecraft.getInstance().screen == null) {
				CRAWLKEY.consumeClick();
			}
		}
	}
}
