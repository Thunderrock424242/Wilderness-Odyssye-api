
package net.mcreator.wildernessoddesyapi.item;

import top.theillusivec4.curios.api.type.capability.ICurioItem;

import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;

public class FrostHeartClanSpiritGemItem extends Item implements ICurioItem {
	public FrostHeartClanSpiritGemItem() {
		super(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC));
	}
}
