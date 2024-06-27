package net.mcreator.wildernessoddesyapi;

import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.mcreator.wildernessoddesyapi.network.WildernessOddesyApiModVariables;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

@Mod(WildernessOddesyApiMod.MODID)
public class VaribbleMain {
    public static final String MODID = "wilderness_oddesy_api";
    private static final Logger LOGGER = LogManager.getLogger();

    public VaribbleMain() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        WildernessOddesyApiModVariables.ATTACHMENT_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    private void setup(final FMLCommonSetupEvent event) {
        LOGGER.info("Wilderness Oddesy API Mod: Setup event");
        WildernessOddesyApiModVariables.init(event);
    }

    public static void addNetworkMessage(ResourceLocation id, Supplier<CustomPacketPayload> supplier, BiConsumer<CustomPacketPayload, PlayPayloadContext> consumer) {
        // Implementation of the network message registration
    }
}
