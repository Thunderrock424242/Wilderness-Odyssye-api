package net.mcreator.wildernessoddesyapi;

import java.util.logging.Logger;
import java.util.logging.Level; // Java logging Level
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.core.BlockPos;

public class CustomItem extends Item {
    private static final Logger LOGGER = Logger.getLogger(CustomItem.class.getName());
    private static final GenericCacheManager<String, CustomItemData> itemCache = new GenericCacheManager<>();
    private static final GenericCacheManager<String, CustomPlayerData> playerCache = new GenericCacheManager<>();
    private static final GenericCacheManager<BlockPos, CustomBlockData> blockCache = new GenericCacheManager<>();
    private static final GenericCacheManager<String, CustomBiomeData> biomeCache = new GenericCacheManager<>();
    private static final GenericCacheManager<String, CustomWorldGenData> worldGenCache = new GenericCacheManager<>();
    private static final GenericCacheManager<String, CustomConfigData> configCache = new GenericCacheManager<>();
    private static final GenericCacheManager<String, CustomPathfindingData> pathfindingCache = new GenericCacheManager<>();
    private static final GenericCacheManager<String, CustomWeatherData> weatherCache = new GenericCacheManager<>();

    public CustomItem(Properties properties) {
        super(properties);
        LOGGER.info("CustomItem initialized with properties: " + properties);
    }

    @Override
    public void onCraftedBy(ItemStack stack, net.minecraft.world.level.Level world, Player player) {
        super.onCraftedBy(stack, world, player);

        // Store custom item data in the cache
        CustomItemData itemData = new CustomItemData(stack.getHoverName().getString(), calculateItemValue(stack));
        itemCache.put(stack.getDescriptionId(), itemData);

        // Store custom player data in the cache
        CustomPlayerData playerData = new CustomPlayerData(player.getName().getString(), player.experienceLevel);
        playerCache.put(player.getStringUUID(), playerData);

        LOGGER.info("Item crafted: " + stack.getHoverName().getString() + " by player: " + player.getName().getString());
    }

    @Override
    public void inventoryTick(ItemStack stack, net.minecraft.world.level.Level world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);

        // Retrieve custom item data from the cache
        CustomItemData itemData = itemCache.get(stack.getDescriptionId());
        if (itemData != null) {
            // Use the custom item data
            applyCustomEffects(entity, itemData);
            LOGGER.info("Applying custom effects to entity: " + entity.getName().getString());
        }

        // Example of using block data
        BlockPos pos = entity.blockPosition();
        CustomBlockData blockData = blockCache.get(pos);
        if (blockData != null) {
            // Use custom block data (placeholder logic)
            LOGGER.info("Block Type: " + blockData.getBlockType() + ", Hardness: " + blockData.getBlockHardness());
        }
    }

    private int calculateItemValue(ItemStack stack) {
        // Custom logic to calculate item value
        int value = stack.getCount() * 10;
        LOGGER.info("Calculated item value: " + value + " for stack: " + stack.getHoverName().getString());
        return value;
    }

    private void applyCustomEffects(Entity entity, CustomItemData data) {
        // Custom logic to apply effects based on item data
        if (entity instanceof Player) {
            ((Player) entity).addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 100, data.getItemValue() / 10));
            LOGGER.info("Applied DAMAGE_BOOST effect to player: " + ((Player) entity).getName().getString());
        }
    }

    public static void addBlockData(BlockPos pos, String blockType, int hardness) {
        CustomBlockData blockData = new CustomBlockData(blockType, hardness);
        blockCache.put(pos, blockData);
        LOGGER.info("Added block data to cache: " + blockType + " at " + pos);
    }

    public static void addBiomeData(String biomeId, String biomeName, float temperature, float humidity) {
        CustomBiomeData biomeData = new CustomBiomeData(biomeName, temperature, humidity);
        biomeCache.put(biomeId, biomeData);
        LOGGER.info("Added biome data to cache: " + biomeName);
    }

    public static void addWorldGenData(String structureId, String structureName, int complexity) {
        CustomWorldGenData worldGenData = new CustomWorldGenData(structureName, complexity);
        worldGenCache.put(structureId, worldGenData);
        LOGGER.info("Added world gen data to cache: " + structureName);
    }

    public static void addConfigData(String configId, String configName, String configValue) {
        CustomConfigData configData = new CustomConfigData(configName, configValue);
        configCache.put(configId, configData);
        LOGGER.info("Added config data to cache: " + configName);
    }

    public static void addPathfindingData(String entityId, BlockPos[] path) {
        CustomPathfindingData pathfindingData = new CustomPathfindingData(entityId, path);
        pathfindingCache.put(entityId, pathfindingData);
        LOGGER.info("Added pathfinding data to cache for entity: " + entityId);
    }

    public static void addWeatherData(String weatherId, String weatherType, long duration, boolean isSevere) {
        CustomWeatherData weatherData = new CustomWeatherData(weatherType, duration, isSevere);
        weatherCache.put(weatherId, weatherData);
        LOGGER.info("Added weather data to cache: " + weatherType);
    }
}
