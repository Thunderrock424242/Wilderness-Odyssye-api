
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.wildernessoddesyapi.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

import net.minecraft.world.level.block.Block;
import net.minecraft.core.registries.BuiltInRegistries;

import net.mcreator.wildernessoddesyapi.block.ThevaultPortalBlock;
import net.mcreator.wildernessoddesyapi.block.PortalframeBlock;
import net.mcreator.wildernessoddesyapi.WildernessOddesyApiMod;

public class WildernessOddesyApiModBlocks {
	public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(BuiltInRegistries.BLOCK, WildernessOddesyApiMod.MODID);
	public static final DeferredHolder<Block, Block> PORTALFRAME = REGISTRY.register("portalframe", () -> new PortalframeBlock());
	public static final DeferredHolder<Block, Block> THEVAULT_PORTAL = REGISTRY.register("thevault_portal", () -> new ThevaultPortalBlock());
	// Start of user code block custom blocks
	// End of user code block custom blocks
}
