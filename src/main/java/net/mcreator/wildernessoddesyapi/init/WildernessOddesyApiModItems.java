
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.wildernessoddesyapi.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

import net.minecraft.world.item.Item;

import net.mcreator.wildernessoddesyapi.item.FireclanSpiritItem;
import net.mcreator.wildernessoddesyapi.WildernessOddesyApiMod;

public class WildernessOddesyApiModItems {
	public static final DeferredRegister.Items REGISTRY = DeferredRegister.createItems(WildernessOddesyApiMod.MODID);
	public static final DeferredHolder<Item, Item> FIRECLAN_SPIRIT = REGISTRY.register("fireclan_spirit", FireclanSpiritItem::new);
	// Start of user code block custom items
	// End of user code block custom items
}
