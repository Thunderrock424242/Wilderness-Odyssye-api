package net.mcreator.wildernessoddesyapi;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.event.server.ServerTickEvent;
import net.neoforged.network.simple.SimpleChannel;
import net.neoforged.network.simple.SimpleChannel.MessageBuilder;
import net.neoforged.event.entity.player.PlayerTickEvent;
import net.neoforged.event.world.WorldTickEvent;
import net.neoforged.event.server.ServerStartingEvent;
import net.neoforged.event.server.ServerStoppingEvent;
import net.neoforged.network.simple.SimpleChannel.MessageBuilder;
import net.neoforged.network.simple.SimpleChannel;
import net.minecraftforge.network.NetworkEvent;

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
        } else if (event.phase == ServerTickEvent.Phase.END) {
            // Code to run at the end of each tick
        }
    }

    private void setupNetworkChannel() {
        channel = NetworkRegistry.newSimpleChannel(
            new ResourceLocation("serverstability", "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
        );

        channel.messageBuilder(MyNetworkMessage.class, 1)
            .decoder(MyNetworkMessage::decode)
            .encoder(MyNetworkMessage::encode)
            .consumerMainThread(MyNetworkMessage::handle)
            .add();
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
                }
            });
            ctx.setPacketHandled(true);
        }
    }

    // Event Handling
    public static class EventHandler {

        @SubscribeEvent
        public void onPlayerTick(PlayerTickEvent event) {
            ServerPlayer player = (ServerPlayer) event.player;
            // Optimize player tick handling
        }

        @SubscribeEvent
        public void onWorldTick(WorldTickEvent event) {
            // Optimize world tick handling
        }

        @SubscribeEvent
        public void onServerStarting(ServerStartingEvent event) {
            // Register server start logic
        }

        @SubscribeEvent
        public void onServerStopping(ServerStoppingEvent event) {
            // Register server stop logic
        }
    }

    // Register Event Handlers
    @Mod.EventBusSubscriber(modid = "serverstability", bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEventSubscriber {

        @SubscribeEvent
        public static void onServerStarting(ServerStartingEvent event) {
            // Register server start logic
        }

        @SubscribeEvent
        public static void onServerStopping(ServerStoppingEvent event) {
            // Register server stop logic
        }

        @SubscribeEvent
        public static void onCommonSetup(FMLCommonSetupEvent event) {
            // Register common setup logic
        }

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            // Register client setup logic
        }
    }
}
