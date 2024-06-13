
/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.wildernessoddesyapi.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.core.registries.Registries;

import net.mcreator.wildernessoddesyapi.potion.FlashSMobEffect;
import net.mcreator.wildernessoddesyapi.WildernessOddesyApiMod;

public class WildernessOddesyApiModMobEffects {
	public static final DeferredRegister<MobEffect> REGISTRY = DeferredRegister.create(Registries.MOB_EFFECT, WildernessOddesyApiMod.MODID);
	public static final DeferredHolder<MobEffect, MobEffect> FLASH_S = REGISTRY.register("flash_s", () -> new FlashSMobEffect());
}
