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

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Supplier;

@Mod(NetworkOptimizerMod.MODID)
public class NetworkOptimizer {
    public static final String MODID = "networkoptimizer";
    private static final String PROTOCOL_VERSION = "1";
    public static SimpleChannel CHANNEL;

    public NetworkOptimizer() {
        NeoForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void setup(final FMLCommonSetupEvent event) {
        CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(MODID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
        );

        int packetId = 0;
        CHANNEL.registerMessage(packetId++, MyPacket.class, MyPacket::encode, MyPacket::decode, MyPacket::handle);
    }

    @SubscribeEvent
    public void onClientSetup(final FMLClientSetupEvent event) {
        // Client-only setup can be added here if needed
    }

    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event) {
        // Handle any server-side logic per tick
    }

    @Mod.EventBusSubscriber(modid = NetworkOptimizerMod.MODID)
    public static class ModEventHandler {
        @SubscribeEvent
        public static void onServerStarting(FMLServerStartingEvent event) {
            // Register commands, etc.
        }
    }

    public static class MyPacket {
        private final int data;

        public MyPacket(int data) {
            this.data = data;
        }

        public static void encode(MyPacket packet, FriendlyByteBuf buffer) {
            buffer.writeInt(packet.data);
        }

        public static MyPacket decode(FriendlyByteBuf buffer) {
            return new MyPacket(buffer.readInt());
        }

        public static void handle(MyPacket packet, Supplier<NetworkEvent.Context> contextSupplier) {
            NetworkEvent.Context context = contextSupplier.get();
            context.enqueueWork(() -> {
                // Handle packet on main thread
                // Example: Send response or handle data
                System.out.println("Received packet with data: " + packet.data);
            });
            context.setPacketHandled(true);
        }
    }

    public static class NetworkHelper {
        public static void sendToPlayer(ServerPlayer player, MyPacket packet) {
            NetworkOptimizerMod.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), packet);
        }

        public static void sendToServer(MyPacket packet) {
            NetworkOptimizerMod.CHANNEL.sendToServer(packet);
        }
    }
}
