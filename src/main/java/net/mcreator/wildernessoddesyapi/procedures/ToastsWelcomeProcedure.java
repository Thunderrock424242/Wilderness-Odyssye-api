package net.mcreator.wildernessoddesyapi.procedures;

import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.client.Minecraft;

import net.mcreator.wildernessoddesyapi.client.toasts.WelcomeToWildernessOddessyToast;

import javax.annotation.Nullable;

@EventBusSubscriber
public class ToastsWelcomeProcedure {
	@SubscribeEvent
	public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
		execute(event);
	}

	public static void execute() {
		execute(null);
	}

	private static void execute(@Nullable Event event) {
		Minecraft.getInstance().getToasts().addToast(new WelcomeToWildernessOddessyToast());
	}
}
