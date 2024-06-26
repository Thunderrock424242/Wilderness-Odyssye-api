package net.mcreator.wildernessoddesyapi;

import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import net.minecraft.core.BlockPos;

class GenericCacheManager<K, V> {
    private static final Logger LOGGER = Logger.getLogger(GenericCacheManager.class.getName());
    private final Map<K, WeakReference<V>> cache = new ConcurrentHashMap<>();
    private final ScheduledExecutorService cacheCleaner = Executors.newSingleThreadScheduledExecutor();

    public GenericCacheManager() {
        // Periodically clean up the cache
        cacheCleaner.scheduleAtFixedRate(() -> {
            cache.entrySet().removeIf(entry -> entry.getValue().get() == null);
            LOGGER.info("Cache cleaned up.");
        }, 1, 1, TimeUnit.MINUTES);
    }

    public void put(K key, V value) {
        cache.put(key, new WeakReference<>(value));
        LOGGER.fine("Added to cache: " + key); // Use a lower logging level for frequent operations
    }

    public V get(K key) {
        WeakReference<V> reference = cache.get(key);
        V value = reference != null ? reference.get() : null;
        LOGGER.fine("Retrieved from cache: " + key + " -> " + value);
        return value;
    }

    public void shutdown() {
        try {
            cacheCleaner.shutdown();
            if (!cacheCleaner.awaitTermination(60, TimeUnit.SECONDS)) {
                cacheCleaner.shutdownNow();
            }
        } catch (InterruptedException e) {
            cacheCleaner.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}

// Enhanced Custom Data Classes

class CustomItemData {
    private final String itemName;
    private final int itemValue;
    private final String rarity;
    private final String description;

    public CustomItemData(String itemName, int itemValue, String rarity, String description) {
        this.itemName = itemName;
        this.itemValue = itemValue;
        this.rarity = rarity;
        this.description = description;
    }

    public String getItemName() {
        return itemName;
    }

    public int getItemValue() {
        return itemValue;
    }

    public String getRarity() {
        return rarity;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "CustomItemData{" +
                "itemName='" + itemName + '\'' +
                ", itemValue=" + itemValue +
                ", rarity='" + rarity + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CustomItemData that = (CustomItemData) o;

        if (itemValue != that.itemValue) return false;
        if (!itemName.equals(that.itemName)) return false;
        if (!rarity.equals(that.rarity)) return false;
        return description.equals(that.description);
    }

    @Override
    public int hashCode() {
        int result = itemName.hashCode();
        result = 31 * result + itemValue;
        result = 31 * result + rarity.hashCode();
        result = 31 * result + description.hashCode();
        return result;
    }
}

class CustomPlayerData {
    private final String playerName;
    private final int playerLevel;
    private final int experiencePoints;
    private final List<String> inventory;

    public CustomPlayerData(String playerName, int playerLevel, int experiencePoints, List<String> inventory) {
        this.playerName = playerName;
        this.playerLevel = playerLevel;
        this.experiencePoints = experiencePoints;
        this.inventory = inventory;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getPlayerLevel() {
        return playerLevel;
    }

    public int getExperiencePoints() {
        return experiencePoints;
    }

    public List<String> getInventory() {
        return inventory;
    }

    @Override
    public String toString() {
        return "CustomPlayerData{" +
                "playerName='" + playerName + '\'' +
                ", playerLevel=" + playerLevel +
                ", experiencePoints=" + experiencePoints +
                ", inventory=" + inventory +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CustomPlayerData that = (CustomPlayerData) o;

        if (playerLevel != that.playerLevel) return false;
        if (experiencePoints != that.experiencePoints) return false;
        if (!playerName.equals(that.playerName)) return false;
        return inventory.equals(that.inventory);
    }

    @Override
    public int hashCode() {
        int result = playerName.hashCode();
        result = 31 * result + playerLevel;
        result = 31 * result + experiencePoints;
        result = 31 * result + inventory.hashCode();
        return result;
    }
}

class CustomBlockData {
    private final String blockType;
    private final int blockHardness;
    private final int resistance;
    private final String toolRequired;

    public CustomBlockData(String blockType, int blockHardness, int resistance, String toolRequired) {
        this.blockType = blockType;
        this.blockHardness = blockHardness;
        this.resistance = resistance;
        this.toolRequired = toolRequired;
    }

    public String getBlockType() {
        return blockType;
    }

    public int getBlockHardness() {
        return blockHardness;
    }

    public int getResistance() {
        return resistance;
    }

    public String getToolRequired() {
        return toolRequired;
    }

    @Override
    public String toString() {
        return "CustomBlockData{" +
                "blockType='" + blockType + '\'' +
                ", blockHardness=" + blockHardness +
                ", resistance=" + resistance +
                ", toolRequired='" + toolRequired + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CustomBlockData that = (CustomBlockData) o;

        if (blockHardness != that.blockHardness) return false;
        if (resistance != that.resistance) return false;
        if (!blockType.equals(that.blockType)) return false;
        return toolRequired.equals(that.toolRequired);
    }

    @Override
    public int hashCode() {
        int result = blockType.hashCode();
        result = 31 * result + blockHardness;
        result = 31 * result + resistance;
        result = 31 * result + toolRequired.hashCode();
        return result;
    }
}

class CustomBiomeData {
    private final String biomeName;
    private final float temperature;
    private final float humidity;
    private final float rainfall;
    private final int altitude;

    public CustomBiomeData(String biomeName, float temperature, float humidity, float rainfall, int altitude) {
        this.biomeName = biomeName;
        this.temperature = temperature;
        this.humidity = humidity;
        this.rainfall = rainfall;
        this.altitude = altitude;
    }

    public String getBiomeName() {
        return biomeName;
    }

    public float getTemperature() {
        return temperature;
    }

    public float getHumidity() {
        return humidity;
    }

    public float getRainfall() {
        return rainfall;
    }

    public int getAltitude() {
        return altitude;
    }

    @Override
    public String toString() {
        return "CustomBiomeData{" +
                "biomeName='" + biomeName + '\'' +
                ", temperature=" + temperature +
                ", humidity=" + humidity +
                ", rainfall=" + rainfall +
                ", altitude=" + altitude +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CustomBiomeData that = (CustomBiomeData) o;

        if (Float.compare(that.temperature, temperature) != 0) return false;
        if (Float.compare(that.humidity, humidity) != 0) return false;
        if (Float.compare(that.rainfall, rainfall) != 0) return false;
        if (altitude != that.altitude) return false;
        return biomeName.equals(that.biomeName);
    }

    @Override
    public int hashCode() {
        int result = biomeName.hashCode();
        result = 31 * result + (temperature != +0.0f ? Float.floatToIntBits(temperature) : 0);
        result = 31 * result + (humidity != +0.0f ? Float.floatToIntBits(humidity) : 0);
        result = 31 * result + (rainfall != +0.0f ? Float.floatToIntBits(rainfall) : 0);
        result = 31 * result + altitude;
        return result;
    }
}

class CustomWorldGenData {
    private final String structureName;
    private final int complexity;
    private final double spawnRate;
    private final List<String> biomeTypes;

    public CustomWorldGenData(String structureName, int complexity, double spawnRate, List<String> biomeTypes) {
        this.structureName = structureName;
        this.complexity = complexity;
        this.spawnRate = spawnRate;
        this.biomeTypes = biomeTypes;
    }

    public String getStructureName() {
        return structureName;
    }

    public int getComplexity() {
        return complexity;
    }

    public double getSpawnRate() {
        return spawnRate;
    }

    public List<String> getBiomeTypes() {
        return biomeTypes;
    }

    @Override
    public String toString() {
        return "CustomWorldGenData{" +
                "structureName='" + structureName + '\'' +
                ", complexity=" + complexity +
                ", spawnRate=" + spawnRate +
                ", biomeTypes=" + biomeTypes +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CustomWorldGenData that = (CustomWorldGenData) o;

        if (complexity != that.complexity) return false;
        if (Double.compare(that.spawnRate, spawnRate) != 0) return false;
        if (!structureName.equals(that.structureName)) return false;
        return biomeTypes.equals(that.biomeTypes);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = structureName.hashCode();
        result = 31 * result + complexity;
        temp = Double.doubleToLongBits(spawnRate);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + biomeTypes.hashCode();
        return result;
    }
}

class CustomConfigData {
    private final String configName;
    private final String configValue;
    private final String defaultValue;
    private final boolean isEnabled;

    public CustomConfigData(String configName, String configValue, String defaultValue, boolean isEnabled) {
        this.configName = configName;
        this.configValue = configValue;
        this.defaultValue = defaultValue;
        this.isEnabled = isEnabled;
    }

    public String getConfigName() {
        return configName;
    }

    public String getConfigValue() {
        return configValue;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    @Override
    public String toString() {
        return "CustomConfigData{" +
                "configName='" + configName + '\'' +
                ", configValue='" + configValue + '\'' +
                ", defaultValue='" + defaultValue + '\'' +
                ", isEnabled=" + isEnabled +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CustomConfigData that = (CustomConfigData) o;

        if (isEnabled != that.isEnabled) return false;
        if (!configName.equals(that.configName)) return false;
        if (!configValue.equals(that.configValue)) return false;
        return defaultValue.equals(that.defaultValue);
    }

    @Override
    public int hashCode() {
        int result = configName.hashCode();
        result = 31 * result + configValue.hashCode();
        result = 31 * result + defaultValue.hashCode();
        result = 31 * result + (isEnabled ? 1 : 0);
        return result;
    }
}

class CustomPathfindingData {
    private final String entityId;
    private final BlockPos[] path;
    private final boolean isComplete;
    private final int currentStep;

    public CustomPathfindingData(String entityId, BlockPos[] path, boolean isComplete, int currentStep) {
        this.entityId = entityId;
        this.path = path;
        this.isComplete = isComplete;
        this.currentStep = currentStep;
    }

    public String getEntityId() {
        return entityId;
    }

    public BlockPos[] getPath() {
        return path;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public int getCurrentStep() {
        return currentStep;
    }

    @Override
    public String toString() {
        return "CustomPathfindingData{" +
                "entityId='" + entityId + '\'' +
                ", path=" + Arrays.toString(path) +
                ", isComplete=" + isComplete +
                ", currentStep=" + currentStep +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CustomPathfindingData that = (CustomPathfindingData) o;

        if (isComplete != that.isComplete) return false;
        if (currentStep != that.currentStep) return false;
        if (!entityId.equals(that.entityId)) return false;
        return Arrays.equals(path, that.path);
    }

    @Override
    public int hashCode() {
        int result = entityId.hashCode();
        result = 31 * result + Arrays.hashCode(path);
        result = 31 * result + (isComplete ? 1 : 0);
        result = 31 * result + currentStep;
        return result;
    }
}

class CustomWeatherData {
    private final String weatherType;
    private final long duration;
    private final boolean isSevere;
    private final int intensity;
    private final double windSpeed;

    public CustomWeatherData(String weatherType, long duration, boolean isSevere, int intensity, double windSpeed) {
        this.weatherType = weatherType;
        this.duration = duration;
        this.isSevere = isSevere;
        this.intensity = intensity;
        this.windSpeed = windSpeed;
    }

    public String getWeatherType() {
        return weatherType;
    }

    public long getDuration() {
        return duration;
    }

    public boolean isSevere() {
        return isSevere;
    }

    public int getIntensity() {
        return intensity;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    @Override
    public String toString() {
        return "CustomWeatherData{" +
                "weatherType='" + weatherType + '\'' +
                ", duration=" + duration +
                ", isSevere=" + isSevere +
                ", intensity=" + intensity +
                ", windSpeed=" + windSpeed +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CustomWeatherData that = (CustomWeatherData) o;

        if (duration != that.duration) return false;
        if (isSevere != that.isSevere) return false;
        if (intensity != that.intensity) return false;
        if (Double.compare(that.windSpeed, windSpeed) != 0) return false;
        return weatherType.equals(that.weatherType);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = weatherType.hashCode();
        result = 31 * result + (int) (duration ^ (duration >>> 32));
        result = 31 * result + (isSevere ? 1 : 0);
        result = 31 * result + intensity;
        temp = Double.doubleToLongBits(windSpeed);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}

class CustomEntityData {
    private final String entityType;
    private final double health;
    private final BlockPos position;

    public CustomEntityData(String entityType, double health, BlockPos position) {
        this.entityType = entityType;
        this.health = health;
        this.position = position;
    }

    public String getEntityType() {
        return entityType;
    }

    public double getHealth() {
        return health;
    }

    public BlockPos getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "CustomEntityData{" +
                "entityType='" + entityType + '\'' +
                ", health=" + health +
                ", position=" + position +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CustomEntityData that = (CustomEntityData) o;

        if (Double.compare(that.health, health) != 0) return false;
        if (!entityType.equals(that.entityType)) return false;
        return position.equals(that.position);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = entityType.hashCode();
        temp = Double.doubleToLongBits(health);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + position.hashCode();
        return result;
    }
}

class CustomEventData {
    private final String eventType;
    private final String eventDescription;
    private final long timestamp;

    public CustomEventData(String eventType, String eventDescription, long timestamp) {
        this.eventType = eventType;
        this.eventDescription = eventDescription;
        this.timestamp = timestamp;
    }

    public String getEventType() {
        return eventType;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "CustomEventData{" +
                "eventType='" + eventType + '\'' +
                ", eventDescription='" + eventDescription + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CustomEventData that = (CustomEventData) o;

        if (timestamp != that.timestamp) return false;
        if (!eventType.equals(that.eventType)) return false;
        return eventDescription.equals(that.eventDescription);
    }

    @Override
    public int hashCode() {
        int result = eventType.hashCode();
        result = 31 * result + eventDescription.hashCode();
        result = 31 * result + (int) (timestamp ^ (timestamp >>> 32));
        return result;
    }
}

class CustomQuestData {
    private final String questName;
    private final String questDescription;
    private final List<String> objectives;
    private final List<String> rewards;

    public CustomQuestData(String questName, String questDescription, List<String> objectives, List<String> rewards) {
        this.questName = questName;
        this.questDescription = questDescription;
        this.objectives = objectives;
        this.rewards = rewards;
    }

    public String getQuestName() {
        return questName;
    }

    public String getQuestDescription() {
        return questDescription;
    }

    public List<String> getObjectives() {
        return objectives;
    }

    public List<String> getRewards() {
        return rewards;
    }

    @Override
    public String toString() {
        return "CustomQuestData{" +
                "questName='" + questName + '\'' +
                ", questDescription='" + questDescription + '\'' +
                ", objectives=" + objectives +
                ", rewards=" + rewards +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CustomQuestData that = (CustomQuestData) o;

        if (!questName.equals(that.questName)) return false;
        if (!questDescription.equals(that.questDescription)) return false;
        if (!objectives.equals(that.objectives)) return false;
        return rewards.equals(that.rewards);
    }

    @Override
    public int hashCode() {
        int result = questName.hashCode();
        result = 31 * result + questDescription.hashCode();
        result = 31 * result + objectives.hashCode();
        result = 31 * result + rewards.hashCode();
        return result;
    }
}

class CustomSkillData {
    private final String skillName;
    private final int skillLevel;
    private final String skillEffect;

    public CustomSkillData(String skillName, int skillLevel, String skillEffect) {
        this.skillName = skillName;
        this.skillLevel = skillLevel;
        this.skillEffect = skillEffect;
    }

    public String getSkillName() {
        return skillName;
    }

    public int getSkillLevel() {
        return skillLevel;
    }

    public String getSkillEffect() {
        return skillEffect;
    }

    @Override
    public String toString() {
        return "CustomSkillData{" +
                "skillName='" + skillName + '\'' +
                ", skillLevel=" + skillLevel +
                ", skillEffect='" + skillEffect + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CustomSkillData that = (CustomSkillData) o;

        if (skillLevel != that.skillLevel) return false;
        if (!skillName.equals(that.skillName)) return false;
        return skillEffect.equals(that.skillEffect);
    }

    @Override
    public int hashCode() {
        int result = skillName.hashCode();
        result = 31 * result + skillLevel;
        result = 31 * result + skillEffect.hashCode();
        return result;
    }
}
