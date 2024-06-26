package net.mcreator.wildernessoddesyapi;

import net.neoforged.network.simple.SimpleChannel;
import net.neoforged.network.NetworkEvent;
import net.neoforged.neoforge.network.registration.NetworkRegistry;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.event.world.WorldTickEvent;
import net.neoforged.event.server.ServerTickEvent;
import net.neoforged.event.server.ServerStoppingEvent;
import net.neoforged.event.server.ServerStartingEvent;
import net.neoforged.event.entity.player.PlayerTickEvent;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.FriendlyByteBuf;

import java.util.function.Supplier;

@Mod("serverstability")
public class ServerStabilityMod {
	private static final String PROTOCOL_VERSION = "1.0";
	private static SimpleChannel channel;

	public ServerStabilityMod() {
		// Register the setup method for modloading
		NeoForge.EVENT_BUS.register(this);
		// Register event handlers
		NeoForge.EVENT_BUS.register(new EventHandler());
	}

	@SubscribeEvent
	public void onCommonSetup(FMLCommonSetupEvent event) {
		// Setup logic here
		setupNetworkChannel();
	}

	@SubscribeEvent
	public void onServerTick(ServerTickEvent event) {
		// Optimized tick handling
		if (event.phase == ServerTickEvent.Phase.START) {
			// Code to run at the start of each tick
			performServerStartTickTasks();
		} else if (event.phase == ServerTickEvent.Phase.END) {
			// Code to run at the end of each tick
			performServerEndTickTasks();
		}
	}

	private void performServerStartTickTasks() {
		// Example logic for tasks to run at the start of each tick
		// This could include checking for scheduled tasks, updating server statistics, etc.
	}

	private void performServerEndTickTasks() {
		// Example logic for tasks to run at the end of each tick
		// This could include saving data, clearing caches, etc.
	}

	private void setupNetworkChannel() {
		channel = NetworkRegistry.newSimpleChannel(new ResourceLocation("serverstability", "main"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);
		channel.messageBuilder(MyNetworkMessage.class, 1).decoder(MyNetworkMessage::decode).encoder(MyNetworkMessage::encode).consumerMainThread(MyNetworkMessage::handle).add();
	}

	// Network Message Handling
	public static class MyNetworkMessage {
		private final int data;

		public MyNetworkMessage(int data) {
			this.data = data;
		}

		public static void encode(MyNetworkMessage msg, FriendlyByteBuf buf) {
			buf.writeInt(msg.data);
		}

		public static MyNetworkMessage decode(FriendlyByteBuf buf) {
			return new MyNetworkMessage(buf.readInt());
		}

		public static void handle(MyNetworkMessage msg, Supplier<NetworkEvent.Context> ctxSupplier) {
			NetworkEvent.Context ctx = ctxSupplier.get();
			ctx.enqueueWork(() -> {
				ServerPlayer player = ctx.getSender();
				if (player != null) {
					// Handle the message on the server side
					// Example: Log the received data
					System.out.println("Received data: " + msg.data);
					// Add your handling logic here
				}
			});
			ctx.setPacketHandled(true);
		}
	}

	// Event Handling
	public static class EventHandler {
		@SubscribeEvent
		public void onPlayerTick(PlayerTickEvent event) {
			if (!event.player.level.isClientSide) {
				ServerPlayer player = (ServerPlayer) event.player;
				// Optimize player tick handling
				performPlayerTickTasks(player);
			}
		}

		@SubscribeEvent
		public void onWorldTick(WorldTickEvent event) {
			if (!event.world.isClientSide) {
				// Optimize world tick handling
				performWorldTickTasks(event.world);
			}
		}

		@SubscribeEvent
		public void onServerStarting(ServerStartingEvent event) {
			// Register server start logic
			System.out.println("Server is starting...");
			initializeServerResources();
		}

		@SubscribeEvent
		public void onServerStopping(ServerStoppingEvent event) {
			// Register server stop logic
			System.out.println("Server is stopping...");
			cleanupServerResources();
		}

		private void performPlayerTickTasks(ServerPlayer player) {
			// Example logic for tasks to run on each player tick
			// This could include checking player stats, updating cooldowns, etc.
		}

		private void performWorldTickTasks(ServerWorld world) {
			// Example logic for tasks to run on each world tick
			// This could include managing world events, updating entities, etc.
		}

		private void initializeServerResources() {
			// Example logic for initializing server resources on start
			// This could include loading configurations, setting up data structures, etc.
		}

		private void cleanupServerResources() {
			// Example logic for cleaning up server resources on stop
			// This could include saving data, releasing resources, etc.
		}
	}

	// Register Event Handlers
	@Mod.EventBusSubscriber(modid = "serverstability", bus = Mod.EventBusSubscriber.Bus.MOD)
	public static class ModEventSubscriber {
		@SubscribeEvent
		public static void onServerStarting(ServerStartingEvent event) {
			// Register server start logic
			System.out.println("Server is starting...");
		}

		@SubscribeEvent
		public static void onServerStopping(ServerStoppingEvent event) {
			// Register server stop logic
			System.out.println("Server is stopping...");
		}

		@SubscribeEvent
		public static void onCommonSetup(FMLCommonSetupEvent event) {
			// Register common setup logic
			System.out.println("Common setup...");
		}

		@SubscribeEvent
		public static void onClientSetup(FMLClientSetupEvent event) {
			// Register client setup logic
			System.out.println("Client setup...");
		}
	}
}
