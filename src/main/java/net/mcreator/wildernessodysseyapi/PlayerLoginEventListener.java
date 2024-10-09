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
