package net.mcreator.wildernessoddesyapi;

import net.minecraft.server.packs.resources.ResourceManager;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class CacheSystem {

    private static final Map<String, Object> cache = new HashMap<>();
    private static final String CACHE_FILE_PATH = "config/wilderness_oddesy_cache.dat";

    // Load data and store it in the cache
    public static void loadData(String key, ResourceManager resourceManager) {
        if (!cache.containsKey(key)) {
            Object data = loadHeavyData(resourceManager);
            cache.put(key, data);
        }
    }

    // Retrieve data from the cache
    public static Object getCachedData(String key) {
        return cache.get(key);
    }

    // Simulate the loading of heavy data; replace with actual logic
    private static Object loadHeavyData(ResourceManager resourceManager) {
        return new Object(); // Replace with actual data loading logic
    }

    // Save cache to disk
    public static void saveCache() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CACHE_FILE_PATH))) {
            oos.writeObject(cache);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load cache from disk
    @SuppressWarnings("unchecked")
    public static void loadCache() {
        File file = new File(CACHE_FILE_PATH);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                Object obj = ois.readObject();
                if (obj instanceof Map) {
                    cache.putAll((Map<String, Object>) obj);
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    // Clear or refresh cache if necessary
    public static void clearCache() {
        cache.clear();
        File file = new File(CACHE_FILE_PATH);
        if (file.exists()) {
            file.delete();
        }
    }
}
//file will get deleted to rename class
