
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.wildernessoddesyapi.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

import net.minecraft.world.item.Item;
import net.minecraft.core.registries.BuiltInRegistries;

import net.mcreator.wildernessoddesyapi.item.FrostHeartClanSpiritGemItem;
import net.mcreator.wildernessoddesyapi.item.FireclanSpiritItem;
import net.mcreator.wildernessoddesyapi.WildernessOddesyApiMod;

public class WildernessOddesyApiModItems {
	public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(BuiltInRegistries.ITEM, WildernessOddesyApiMod.MODID);
	public static final DeferredHolder<Item, Item> FIRECLAN_SPIRIT = REGISTRY.register("fireclan_spirit", FireclanSpiritItem::new);
	public static final DeferredHolder<Item, Item> FROST_HEART_CLAN_SPIRIT_GEM = REGISTRY.register("frost_heart_clan_spirit_gem", FrostHeartClanSpiritGemItem::new);
	// Start of user code block custom items
	// End of user code block custom items
}
