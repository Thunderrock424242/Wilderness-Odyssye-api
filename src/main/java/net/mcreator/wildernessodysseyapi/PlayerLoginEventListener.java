package net.mcreator.wildernessodysseyapi;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.network.FMLNetworkEvent;

@EventBusSubscriber(modid = "wilderness_oddesy_api")
public class PlayerLoginEventListener {

    @SubscribeEvent
    public static void onPlayerLogin(FMLNetworkEvent.ServerConnectionFromClientEvent event) {
        if (config.enable_global_ban) {
            ServerPlayer player = event.getPlayer();
            String playerUUID = player.getStringUUID();

            if (GlobalBanManager.isPlayerGloballyBanned(playerUUID)) {
                player.connection.disconnect(Component.nullToEmpty("You have been globally banned."));
            }
        }
    }
}
