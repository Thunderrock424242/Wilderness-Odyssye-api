/*
 * The code of this mod element is always locked.
 *
 * You can register new events in this class too.
 *
 * If you want to make a plain independent class, create it using
 * Project Browser -> New... and make sure to make the class
 * outside net.mcreator.wildernessoddesyapi as this package is managed by MCreator.
 *
 * If you change workspace package, modid or prefix, you will need
 * to manually adapt this file to these changes or remake it.
 *
 * This class will be added in the mod root package.
*/
package net.mcreator.wildernessoddesyapi;

import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.event.TickEvent.LevelTickEvent;
import net.neoforged.neoforge.event.entity.living.MobSpawnEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext;
import net.neoforged.neoforge.common.NeoForge;
import net.minecraft.world.entity.MobCategory;
import org.apache.commons.lang3.tuple.Pair;

@Mod("utility_qol_for_wilderness_mod")
public class MobStages {
    public static final String MODID = "utility_qol_for_wilderness_mod";
    private static int daysElapsed = 0;
    private static final int BASE_MOB_SPAWN_RATE = 5; // Base rate of mob spawning

    public MobStages() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::setup);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ModConfigHolder.COMMON_SPEC);

        NeoForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        // Initialization code
    }

    @SubscribeEvent
    public static void onWorldTick(LevelTickEvent event) {
        Level world = event.getLevel();
        if (world.dimension() == Level.OVERWORLD && !world.isClientSide) {
            if (world.getDayTime() % 24000 == 0) { // Check if it's a new day
                daysElapsed++;
            }
        }
    }

    @SubscribeEvent
    public static void onMobSpawn(MobSpawnEvent.SpawnPlacementCheck event) {
        if (event.getEntity().getType().getCategory() == MobCategory.MONSTER) {
            Level world = (Level) event.getLevel();
            if (world.dimension() == Level.OVERWORLD) {
                if (daysElapsed <= 20) {
                    int additionalMobs = daysElapsed / BASE_MOB_SPAWN_RATE;
                    int maxMobs = ModConfigHolder.COMMON.maxMobs.get();
                    if (world.random.nextInt(100) < additionalMobs && additionalMobs < maxMobs) {
                        event.setResult(MobSpawnEvent.SpawnPlacementCheck.Result.ALLOW);
                    }
                } else {
                    // Revert to Minecraft's normal spawning system after 20 days
                    event.setResult(MobSpawnEvent.SpawnPlacementCheck.Result.DEFAULT);
                }
            }
        }
    }

    public static class ModConfigHolder {
        public static final Common COMMON;
        public static final ModConfigSpec COMMON_SPEC;

        static {
            Pair<Common, ModConfigSpec> commonSpecPair = new ModConfigSpec.Builder().configure(Common::new);
            COMMON = commonSpecPair.getLeft();
            COMMON_SPEC = commonSpecPair.getRight();
        }

        public static class Common {
            public final ModConfigSpec.IntValue maxMobs;

            public Common(ModConfigSpec.Builder builder) {
                builder.push("general");
                maxMobs = builder
                        .comment("Maximum number of mobs that can spawn")
                        .defineInRange("maxMobs", 100, 1, 1000);
                builder.pop();
            }
        }
    }
}
