package net.mcreator.wildernessoddesyapi.procedures;

import net.minecraft.world.entity.Entity;

import net.mcreator.wildernessoddesyapi.network.WildernessOddesyApiModVariables;

public class SetupcrawlProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (!entity.getData(WildernessOddesyApiModVariables.PLAYER_VARIABLES).is_player_crawling) {
			{
				WildernessOddesyApiModVariables.PlayerVariables _vars = entity.getData(WildernessOddesyApiModVariables.PLAYER_VARIABLES);
				_vars.is_player_crawling = true;
				_vars.syncPlayerVariables(entity);
			}
		}
		assert Boolean.TRUE; //#dbg:Setupcrawl:marker1
	}
}
