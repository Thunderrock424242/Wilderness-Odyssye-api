
package net.mcreator.wildernessoddesyapi.item;

import top.theillusivec4.curios.api.type.capability.ICurioItem;
import top.theillusivec4.curios.api.SlotContext;

import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;

import net.mcreator.wildernessoddesyapi.procedures.FireClanSpiritPProcedure;

public class FireclanSpiritItem extends Item implements ICurioItem {
	public FireclanSpiritItem() {
		super(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON));
	}

	@Override
	public void curioTick(SlotContext slotContext, ItemStack stack) {
		FireClanSpiritPProcedure.execute(slotContext.entity());
	}
}
