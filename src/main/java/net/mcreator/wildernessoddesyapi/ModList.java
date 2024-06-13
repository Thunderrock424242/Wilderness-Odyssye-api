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

import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.api.distmarker.Dist;

import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.loading.moddiscovery.ModInfo;
import net.minecraftforge.fml.loading.moddiscovery.ModList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Mod("modid")
public class MainModClass {
    private static final Logger LOGGER = LogManager.getLogger();

    public MainModClass() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onLoadComplete(FMLLoadCompleteEvent event) {
        List<ModInfo> mods = ModList.get().getMods();
        Map<String, List<String>> dependencies = ModList.get().getModContainerById().entrySet().stream()
                .collect(Collectors.toMap(
                        e -> e.getKey().getModId(),
                        e -> e.getValue().getDependencies().stream().map(d -> d.getModId()).collect(Collectors.toList())
                ));

        LOGGER.info("Loaded Mods:");
        for (ModInfo mod : mods) {
            LOGGER.info("Mod: {} Version: {}", mod.getModId(), mod.getVersion());
            if (dependencies.containsKey(mod.getModId())) {
                LOGGER.info("Dependencies: {}", dependencies.get(mod.getModId()));
            }
        }
    }
}