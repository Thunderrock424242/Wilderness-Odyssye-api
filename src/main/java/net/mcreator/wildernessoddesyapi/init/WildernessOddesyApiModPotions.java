
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.wildernessoddesyapi.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.core.registries.Registries;

import net.mcreator.wildernessoddesyapi.WildernessOddesyApiMod;

public class WildernessOddesyApiModPotions {
	public static final DeferredRegister<Potion> REGISTRY = DeferredRegister.create(Registries.POTION, WildernessOddesyApiMod.MODID);
	public static final DeferredHolder<Potion, Potion> FLASH_SPEED = REGISTRY.register("flash_speed",
			() -> new Potion(new MobEffectInstance(WildernessOddesyApiModMobEffects.FLASH_S.get(), 3600, 0, false, true), new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 3600, 50, false, false),
					new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 3600, 50, false, false), new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 3600, 50, false, false), new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 3600, 50, false, false),
					new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 3600, 50, false, false), new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 3600, 50, false, false), new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 3600, 50, false, false),
					new MobEffectInstance(MobEffects.JUMP, 3600, 20, false, false)));
}
