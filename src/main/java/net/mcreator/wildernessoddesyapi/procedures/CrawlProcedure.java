package net.mcreator.wildernessoddesyapi.procedures;

import net.neoforged.neoforge.event.TickEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.Entity;

import net.mcreator.wildernessoddesyapi.network.WildernessOddesyApiModVariables;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class CrawlProcedure {
	@SubscribeEvent
	public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
		if (event.phase == TickEvent.Phase.END) {
			execute(event, event.player);
		}
	}

	public static void execute(Entity entity) {
		execute(null, entity);
	}

	private static void execute(@Nullable Event event, Entity entity) {
		if (entity == null)
			return;
		if (entity.getData(WildernessOddesyApiModVariables.PLAYER_VARIABLES).is_player_crawling == true) {
			entity.setPose(Pose.SWIMMING);
		}
		assert Boolean.TRUE; //#dbg:Crawl:marker1
	}
}
