
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.wildernessoddesyapi.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.bus.api.IEventBus;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.BlockItem;
import net.minecraft.core.registries.BuiltInRegistries;

import net.mcreator.wildernessoddesyapi.item.ThevaultItem;
import net.mcreator.wildernessoddesyapi.item.LightningbowItem;
import net.mcreator.wildernessoddesyapi.item.LightningarrowItem;
import net.mcreator.wildernessoddesyapi.WildernessOddesyApiMod;

public class WildernessOddesyApiModItems {
	public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(BuiltInRegistries.ITEM, WildernessOddesyApiMod.MODID);
<<<<<<< HEAD
=======
	public static final DeferredHolder<Item, Item> LIGHTINGENERGYBLOCK = block(WildernessOddesyApiModBlocks.LIGHTINGENERGYBLOCK);
	public static final DeferredHolder<Item, Item> LIGHTNINGARROW = REGISTRY.register("lightningarrow", () -> new LightningarrowItem());
	public static final DeferredHolder<Item, Item> LIGHTNINGBOW = REGISTRY.register("lightningbow", () -> new LightningbowItem());
<<<<<<< HEAD
>>>>>>> parent of b7899b3 (added back some files and deleted lots of things that were not needed)
=======
>>>>>>> parent of b7899b3 (added back some files and deleted lots of things that were not needed)
	public static final DeferredHolder<Item, Item> PORTALFRAME = block(WildernessOddesyApiModBlocks.PORTALFRAME);
	public static final DeferredHolder<Item, Item> THEVAULT = REGISTRY.register("thevault", () -> new ThevaultItem());

	// Start of user code block custom items
	// End of user code block custom items
	public static void register(IEventBus bus) {
		REGISTRY.register(bus);
	}

	private static DeferredHolder<Item, Item> block(DeferredHolder<Block, Block> block) {
		return REGISTRY.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties()));
	}
}
