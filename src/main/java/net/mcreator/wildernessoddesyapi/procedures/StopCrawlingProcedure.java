package net.mcreator.wildernessoddesyapi.procedures;

import net.minecraft.world.entity.Entity;

import net.mcreator.wildernessoddesyapi.network.WildernessOddesyApiModVariables;

public class StopCrawlingProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (entity.getData(WildernessOddesyApiModVariables.PLAYER_VARIABLES).Is_player_crawling) {
			{
				WildernessOddesyApiModVariables.PlayerVariables _vars = entity.getData(WildernessOddesyApiModVariables.PLAYER_VARIABLES);
				_vars.Is_player_crawling = false;
				_vars.syncPlayerVariables(entity);
			}
		}
		assert Boolean.TRUE; //#dbg:StopCrawling:marker1
	}
}
