
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.wildernessoddesyapi.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.Registries;

import net.mcreator.wildernessoddesyapi.WildernessOddesyApiMod;

public class WildernessOddesyApiModTabs {
	public static final DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, WildernessOddesyApiMod.MODID);
	public static final DeferredHolder<CreativeModeTab, CreativeModeTab> WILDERNESS_ODESSY_API = REGISTRY.register("wilderness_odessy_api",
			() -> CreativeModeTab.builder().title(Component.translatable("item_group.wilderness_oddesy_api.wilderness_odessy_api")).icon(() -> new ItemStack(WildernessOddesyApiModItems.FIRECLAN_SPIRIT.get())).displayItems((parameters, tabData) -> {
				tabData.accept(WildernessOddesyApiModItems.FIRECLAN_SPIRIT.get());
				tabData.accept(WildernessOddesyApiModItems.FROST_HEART_CLAN_SPIRIT_GEM.get());
			})

					.build());
}
