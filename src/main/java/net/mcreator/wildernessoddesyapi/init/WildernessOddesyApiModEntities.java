
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.wildernessoddesyapi.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.registries.Registries;

import net.mcreator.wildernessoddesyapi.entity.LightningarrowprojectileEntity;
import net.mcreator.wildernessoddesyapi.WildernessOddesyApiMod;

public class WildernessOddesyApiModEntities {
	public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(Registries.ENTITY_TYPE, WildernessOddesyApiMod.MODID);
	public static final DeferredHolder<EntityType<?>, EntityType<LightningarrowprojectileEntity>> LIGHTNINGARROWPROJECTILE = register("lightningarrowprojectile",
			EntityType.Builder.<LightningarrowprojectileEntity>of(LightningarrowprojectileEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(0.5f, 0.5f));

	private static <T extends Entity> DeferredHolder<EntityType<?>, EntityType<T>> register(String registryname, EntityType.Builder<T> entityTypeBuilder) {
		return REGISTRY.register(registryname, () -> (EntityType<T>) entityTypeBuilder.build(registryname));
	}
}
