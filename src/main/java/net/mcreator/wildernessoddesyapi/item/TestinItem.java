
package net.mcreator.wildernessoddesyapi.item;

import net.minecraft.world.item.RecordItem;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;

public class TestinItem extends RecordItem {
	public TestinItem() {
		super(0, () -> BuiltInRegistries.SOUND_EVENT.get(new ResourceLocation("wilderness_oddesy_api:i_ran_so_far_away")), new Item.Properties().stacksTo(1).rarity(Rarity.EPIC), 100);
	}
}
