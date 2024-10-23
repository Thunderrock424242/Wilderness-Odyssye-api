package net.mcreator.wildernessodysseyapi.network;

import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.chat.Component;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.HolderLookup;

import net.mcreator.wildernessodysseyapi.WildernessOdysseyApiMod;

import java.util.function.Supplier;

/**
 * The type Wilderness odyssey api mod variables.
 */
@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class WildernessOdysseyApiModVariables {
    /**
     * The constant ATTACHMENT_TYPES.
     */
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, WildernessOdysseyApiMod.MODID);
    /**
     * The constant PLAYER_VARIABLES.
     */
    public static final Supplier<AttachmentType<PlayerVariables>> PLAYER_VARIABLES = ATTACHMENT_TYPES.register("player_variables", () -> AttachmentType.serializable(() -> new PlayerVariables()).build());

    /**
     * Init.
     *
     * @param event the event
     */
    @SubscribeEvent
	public static void init(FMLCommonSetupEvent event) {
		WildernessOdysseyApiMod.addNetworkMessage(SavedDataSyncMessage.TYPE, SavedDataSyncMessage.STREAM_CODEC, SavedDataSyncMessage::handleData);
		WildernessOdysseyApiMod.addNetworkMessage(PlayerVariablesSyncMessage.TYPE, PlayerVariablesSyncMessage.STREAM_CODEC, PlayerVariablesSyncMessage::handleData);
	}

    /**
     * The type Event bus variable handlers.
     */
    @EventBusSubscriber
	public static class EventBusVariableHandlers {
        /**
         * On player logged in sync player variables.
         *
         * @param event the event
         */
        @SubscribeEvent
		public static void onPlayerLoggedInSyncPlayerVariables(PlayerEvent.PlayerLoggedInEvent event) {
			if (event.getEntity() instanceof ServerPlayer player)
				player.getData(PLAYER_VARIABLES).syncPlayerVariables(event.getEntity());
		}

        /**
         * On player respawned sync player variables.
         *
         * @param event the event
         */
        @SubscribeEvent
		public static void onPlayerRespawnedSyncPlayerVariables(PlayerEvent.PlayerRespawnEvent event) {
			if (event.getEntity() instanceof ServerPlayer player)
				player.getData(PLAYER_VARIABLES).syncPlayerVariables(event.getEntity());
		}

        /**
         * On player changed dimension sync player variables.
         *
         * @param event the event
         */
        @SubscribeEvent
		public static void onPlayerChangedDimensionSyncPlayerVariables(PlayerEvent.PlayerChangedDimensionEvent event) {
			if (event.getEntity() instanceof ServerPlayer player)
				player.getData(PLAYER_VARIABLES).syncPlayerVariables(event.getEntity());
		}

        /**
         * Clone player.
         *
         * @param event the event
         */
        @SubscribeEvent
		public static void clonePlayer(PlayerEvent.Clone event) {
			PlayerVariables original = event.getOriginal().getData(PLAYER_VARIABLES);
			PlayerVariables clone = new PlayerVariables();
			clone.Is_player_crawling = original.Is_player_crawling;
			if (!event.isWasDeath()) {
			}
			event.getEntity().setData(PLAYER_VARIABLES, clone);
		}

        /**
         * On player logged in.
         *
         * @param event the event
         */
        @SubscribeEvent
		public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
			if (event.getEntity() instanceof ServerPlayer player) {
				SavedData mapdata = MapVariables.get(event.getEntity().level());
				SavedData worlddata = WorldVariables.get(event.getEntity().level());
				if (mapdata != null)
					PacketDistributor.sendToPlayer(player, new SavedDataSyncMessage(0, mapdata));
				if (worlddata != null)
					PacketDistributor.sendToPlayer(player, new SavedDataSyncMessage(1, worlddata));
			}
		}

        /**
         * On player changed dimension.
         *
         * @param event the event
         */
        @SubscribeEvent
		public static void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
			if (event.getEntity() instanceof ServerPlayer player) {
				SavedData worlddata = WorldVariables.get(event.getEntity().level());
				if (worlddata != null)
					PacketDistributor.sendToPlayer(player, new SavedDataSyncMessage(1, worlddata));
			}
		}
	}

    /**
     * The type World variables.
     */
    public static class WorldVariables extends SavedData {
        /**
         * The constant DATA_NAME.
         */
        public static final String DATA_NAME = "wilderness_odyssey_api_worldvars";
        /**
         * The Is this a new world.
         */
        public boolean Is_this_a_new_world = true;

        /**
         * Load world variables.
         *
         * @param tag            the tag
         * @param lookupProvider the lookup provider
         * @return the world variables
         */
        public static WorldVariables load(CompoundTag tag, HolderLookup.Provider lookupProvider) {
			WorldVariables data = new WorldVariables();
			data.read(tag, lookupProvider);
			return data;
		}

        /**
         * Read.
         *
         * @param nbt            the nbt
         * @param lookupProvider the lookup provider
         */
        public void read(CompoundTag nbt, HolderLookup.Provider lookupProvider) {
			Is_this_a_new_world = nbt.getBoolean("Is_this_a_new_world");
		}

		@Override
		public CompoundTag save(CompoundTag nbt, HolderLookup.Provider lookupProvider) {
			nbt.putBoolean("Is_this_a_new_world", Is_this_a_new_world);
			return nbt;
		}

        /**
         * Sync data.
         *
         * @param world the world
         */
        public void syncData(LevelAccessor world) {
			this.setDirty();
			if (world instanceof ServerLevel level)
				PacketDistributor.sendToPlayersInDimension(level, new SavedDataSyncMessage(1, this));
		}

        /**
         * The Client side.
         */
        static WorldVariables clientSide = new WorldVariables();

        /**
         * Get world variables.
         *
         * @param world the world
         * @return the world variables
         */
        public static WorldVariables get(LevelAccessor world) {
			if (world instanceof ServerLevel level) {
				return level.getDataStorage().computeIfAbsent(new SavedData.Factory<>(WorldVariables::new, WorldVariables::load), DATA_NAME);
			} else {
				return clientSide;
			}
		}
	}

    /**
     * The type Map variables.
     */
    public static class MapVariables extends SavedData {
        /**
         * The constant DATA_NAME.
         */
        public static final String DATA_NAME = "wilderness_odyssey_api_mapvars";

        /**
         * Load map variables.
         *
         * @param tag            the tag
         * @param lookupProvider the lookup provider
         * @return the map variables
         */
        public static MapVariables load(CompoundTag tag, HolderLookup.Provider lookupProvider) {
			MapVariables data = new MapVariables();
			data.read(tag, lookupProvider);
			return data;
		}

        /**
         * Read.
         *
         * @param nbt            the nbt
         * @param lookupProvider the lookup provider
         */
        public void read(CompoundTag nbt, HolderLookup.Provider lookupProvider) {
		}

		@Override
		public CompoundTag save(CompoundTag nbt, HolderLookup.Provider lookupProvider) {
			return nbt;
		}

        /**
         * Sync data.
         *
         * @param world the world
         */
        public void syncData(LevelAccessor world) {
			this.setDirty();
			if (world instanceof Level && !world.isClientSide())
				PacketDistributor.sendToAllPlayers(new SavedDataSyncMessage(0, this));
		}

        /**
         * The Client side.
         */
        static MapVariables clientSide = new MapVariables();

        /**
         * Get map variables.
         *
         * @param world the world
         * @return the map variables
         */
        public static MapVariables get(LevelAccessor world) {
			if (world instanceof ServerLevelAccessor serverLevelAcc) {
				return serverLevelAcc.getLevel().getServer().getLevel(Level.OVERWORLD).getDataStorage().computeIfAbsent(new SavedData.Factory<>(MapVariables::new, MapVariables::load), DATA_NAME);
			} else {
				return clientSide;
			}
		}
	}

    /**
     * The type Saved data sync message.
     */
    public record SavedDataSyncMessage(int dataType, SavedData data) implements CustomPacketPayload {
        /**
         * The constant TYPE.
         */
        public static final Type<SavedDataSyncMessage> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(WildernessOdysseyApiMod.MODID, "saved_data_sync"));
        /**
         * The constant STREAM_CODEC.
         */
        public static final StreamCodec<RegistryFriendlyByteBuf, SavedDataSyncMessage> STREAM_CODEC = StreamCodec.of((RegistryFriendlyByteBuf buffer, SavedDataSyncMessage message) -> {
			buffer.writeInt(message.dataType);
			if (message.data != null)
				buffer.writeNbt(message.data.save(new CompoundTag(), buffer.registryAccess()));
		}, (RegistryFriendlyByteBuf buffer) -> {
			int dataType = buffer.readInt();
			CompoundTag nbt = buffer.readNbt();
			SavedData data = null;
			if (nbt != null) {
				data = dataType == 0 ? new MapVariables() : new WorldVariables();
				if (data instanceof MapVariables mapVariables)
					mapVariables.read(nbt, buffer.registryAccess());
				else if (data instanceof WorldVariables worldVariables)
					worldVariables.read(nbt, buffer.registryAccess());
			}
			return new SavedDataSyncMessage(dataType, data);
		});

		@Override
		public Type<SavedDataSyncMessage> type() {
			return TYPE;
		}

        /**
         * Handle data.
         *
         * @param message the message
         * @param context the context
         */
        public static void handleData(final SavedDataSyncMessage message, final IPayloadContext context) {
			if (context.flow() == PacketFlow.CLIENTBOUND && message.data != null) {
				context.enqueueWork(() -> {
					if (message.dataType == 0)
						MapVariables.clientSide.read(message.data.save(new CompoundTag(), context.player().registryAccess()), context.player().registryAccess());
					else
						WorldVariables.clientSide.read(message.data.save(new CompoundTag(), context.player().registryAccess()), context.player().registryAccess());
				}).exceptionally(e -> {
					context.connection().disconnect(Component.literal(e.getMessage()));
					return null;
				});
			}
		}
	}

    /**
     * The type Player variables.
     */
    public static class PlayerVariables implements INBTSerializable<CompoundTag> {
        /**
         * The Is player crawling.
         */
        public boolean Is_player_crawling = false;

		@Override
		public CompoundTag serializeNBT(HolderLookup.Provider lookupProvider) {
			CompoundTag nbt = new CompoundTag();
			nbt.putBoolean("Is_player_crawling", Is_player_crawling);
			return nbt;
		}

		@Override
		public void deserializeNBT(HolderLookup.Provider lookupProvider, CompoundTag nbt) {
			Is_player_crawling = nbt.getBoolean("Is_player_crawling");
		}

        /**
         * Sync player variables.
         *
         * @param entity the entity
         */
        public void syncPlayerVariables(Entity entity) {
			if (entity instanceof ServerPlayer serverPlayer)
				PacketDistributor.sendToPlayer(serverPlayer, new PlayerVariablesSyncMessage(this));
		}
	}

    /**
     * The type Player variables sync message.
     */
    public record PlayerVariablesSyncMessage(PlayerVariables data) implements CustomPacketPayload {
        /**
         * The constant TYPE.
         */
        public static final Type<PlayerVariablesSyncMessage> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(WildernessOdysseyApiMod.MODID, "player_variables_sync"));
        /**
         * The constant STREAM_CODEC.
         */
        public static final StreamCodec<RegistryFriendlyByteBuf, PlayerVariablesSyncMessage> STREAM_CODEC = StreamCodec
				.of((RegistryFriendlyByteBuf buffer, PlayerVariablesSyncMessage message) -> buffer.writeNbt(message.data().serializeNBT(buffer.registryAccess())), (RegistryFriendlyByteBuf buffer) -> {
					PlayerVariablesSyncMessage message = new PlayerVariablesSyncMessage(new PlayerVariables());
					message.data.deserializeNBT(buffer.registryAccess(), buffer.readNbt());
					return message;
				});

		@Override
		public Type<PlayerVariablesSyncMessage> type() {
			return TYPE;
		}

        /**
         * Handle data.
         *
         * @param message the message
         * @param context the context
         */
        public static void handleData(final PlayerVariablesSyncMessage message, final IPayloadContext context) {
			if (context.flow() == PacketFlow.CLIENTBOUND && message.data != null) {
				context.enqueueWork(() -> context.player().getData(PLAYER_VARIABLES).deserializeNBT(context.player().registryAccess(), message.data.serializeNBT(context.player().registryAccess()))).exceptionally(e -> {
					context.connection().disconnect(Component.literal(e.getMessage()));
					return null;
				});
			}
		}
	}
}
