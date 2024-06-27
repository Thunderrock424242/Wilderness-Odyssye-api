package net.mcreator.wildernessoddesyapi;

import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living;
import net.neoforged.bus.api.IEventBus;

@Mod("wilderness_oddesy_api")
public class WolfThunderMixinEventHandler {

    public WolfThunderMixinEventHandler() {
        IEventBus modEventBus = NeoForge.EVENT_BUS;
        modEventBus.addListener(this::setup);

        NeoForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        // Pre-init code
    }

    @OnlyIn(Dist.CLIENT)
    private void doClientStuff(final FMLClientSetupEvent event) {
        // Client-specific code
    }

    @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        // Your event handling code here
    }
}
