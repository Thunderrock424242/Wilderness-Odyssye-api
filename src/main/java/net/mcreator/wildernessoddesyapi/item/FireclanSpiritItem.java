
package net.mcreator.wildernessoddesyapi.item;

import top.theillusivec4.curios.api.type.capability.ICurioItem;
import top.theillusivec4.curios.api.SlotContext;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;

import net.mcreator.wildernessoddesyapi.procedures.FireClanSpiritPProcedure;

public class FireclanSpiritItem extends Item implements ICurioItem {
	public FireclanSpiritItem() {
		super(new Item.Properties().stacksTo(1).fireResistant().rarity(Rarity.EPIC));
	}

	@Override
	public float getDestroySpeed(ItemStack itemstack, BlockState state) {
		return 0.1f;
	}

	@Override
	public void curioTick(SlotContext slotContext, ItemStack stack) {
		FireClanSpiritPProcedure.execute(slotContext.entity());
	}
}
