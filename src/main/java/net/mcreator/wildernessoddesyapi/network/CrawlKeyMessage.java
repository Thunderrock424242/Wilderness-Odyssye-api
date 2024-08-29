
package net.mcreator.wildernessoddesyapi.network;

import net.mcreator.wildernessoddesyapi.procedures.StopCrawlingProcedure;
import net.mcreator.wildernessoddesyapi.procedures.SetupCrawlProcedure;
import net.mcreator.wildernessoddesyapi.WildernessOddesyApiMod;

import java.lang.reflect.Type;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public record CrawlKeyMessage(int eventType, int pressedms) implements CustomPacketPayload {
	public static final Type<CrawlKeyMessage> TYPE = new Type<>(new ResourceLocation(WildernessOddesyApiMod.MODID, "key_crawl_key"));
	public static final StreamCodec<RegistryFriendlyByteBuf, CrawlKeyMessage> STREAM_CODEC = StreamCodec.of((RegistryFriendlyByteBuf buffer, CrawlKeyMessage message) -> {
		buffer.writeInt(message.eventType);
		buffer.writeInt(message.pressedms);
	}, (RegistryFriendlyByteBuf buffer) -> new CrawlKeyMessage(buffer.readInt(), buffer.readInt()));

	@Override
	public Type<CrawlKeyMessage> type() {
		return TYPE;
	}

	public static void handleData(final CrawlKeyMessage message, final IPayloadContext context) {
		if (context.flow() == PacketFlow.SERVERBOUND) {
			context.enqueueWork(() -> {
				pressAction(context.player(), message.eventType, message.pressedms);
			}).exceptionally(e -> {
				context.connection().disconnect(Component.literal(e.getMessage()));
				return null;
			});
		}
	}

	public static void pressAction(Player entity, int type, int pressedms) {
		Level world = entity.level();
		double x = entity.getX();
		double y = entity.getY();
		double z = entity.getZ();
		// security measure to prevent arbitrary chunk generation
		if (!world.hasChunkAt(entity.blockPosition()))
			return;
		if (type == 0) {

			SetupCrawlProcedure.execute(entity);
		}
		if (type == 1) {

			StopCrawlingProcedure.execute(entity);
		}
	}

	@SubscribeEvent
	public static void registerMessage(FMLCommonSetupEvent event) {
		WildernessOddesyApiMod.addNetworkMessage(CrawlKeyMessage.TYPE, CrawlKeyMessage.STREAM_CODEC, CrawlKeyMessage::handleData);
	}
}
