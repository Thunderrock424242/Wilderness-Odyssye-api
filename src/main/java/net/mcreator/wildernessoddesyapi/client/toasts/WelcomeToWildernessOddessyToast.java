package net.mcreator.wildernessoddesyapi.client.toasts;

public class WelcomeToWildernessOddessyToast implements Toast {
	@Override
	public Visibility render(PoseStack poseStack, ToastComponent component, long lastChanged) {
		RenderSystem.setShaderTexture(0, new ResourceLocation("wilderness_oddesy_api:textures/screens/638291910329297415_1.png"));
		GuiComponent.blit(poseStack, 0, 0, 0, 32, this.width(), this.height());
		component.getMinecraft().font.draw(poseStack, Component.translatable("toasts.wilderness_oddesy_api.welcome_to_wilderness_oddessy.title"), 30, 7, -11534256);
		component.getMinecraft().font.draw(poseStack, Component.translatable("toasts.wilderness_oddesy_api.welcome_to_wilderness_oddessy.description"), 30, 18, -16777216);
		RenderSystem.enableBlend();
		RenderSystem.setShaderTexture(0, new ResourceLocation("wilderness_oddesy_api:textures/screens/638291910329297415_1.png"));
		GuiComponent.blit(poseStack, 6, 6, 296, 120, 20, 20);
		if (lastChanged <= 5000)
			return Visibility.SHOW;
		else
			return Visibility.HIDE;
	}
}
