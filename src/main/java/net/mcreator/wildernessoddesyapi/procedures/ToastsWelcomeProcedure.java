package net.mcreator.wildernessoddesyapi.procedures;

import net.neoforged.bus.api.Event;

import net.mcreator.wildernessoddesyapi.client.toasts.WelcomeToWildernessOddessyToast;

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
