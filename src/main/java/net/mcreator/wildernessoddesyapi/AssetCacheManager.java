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

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

public class AssetCacheManager {

    private static final String CACHE_DIR = "cache";
    private static final String HASH_FILE = "cache_hash.txt";

    private Map<String, byte[]> assetCache;
    private String lastHash;

    public AssetCacheManager() {
        assetCache = new HashMap<>();
        loadCache();
    }

    private void loadCache() {
        File hashFile = new File(CACHE_DIR, HASH_FILE);
        if (hashFile.exists()) {
            lastHash = readHashFromFile(hashFile);
            if (isCacheValid()) {
                loadAssetsFromCache();
            } else {
                clearCache();
            }
        } else {
            clearCache();
        }
    }

    private String readHashFromFile(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            return reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean isCacheValid() {
        String currentHash = calculateCurrentHash();
        return currentHash.equals(lastHash);
    }

    private String calculateCurrentHash() {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            // Hash the files for mods, resource packs, and configs
            // This is a simple example, you might need to adjust it based on your specific needs
            digest.update(Files.readAllBytes(Paths.get("mods")));
            digest.update(Files.readAllBytes(Paths.get("resourcepacks")));
            digest.update(Files.readAllBytes(Paths.get("config")));
            byte[] hash = digest.digest();
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private void loadAssetsFromCache() {
        File cacheDir = new File(CACHE_DIR);
        if (cacheDir.exists() && cacheDir.isDirectory()) {
            for (File file : cacheDir.listFiles()) {
                if (!file.getName().equals(HASH_FILE)) {
                    try {
                        String key = file.getName();
                        byte[] data = Files.readAllBytes(file.toPath());
                        assetCache.put(key, data);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void saveCache() {
        File hashFile = new File(CACHE_DIR, HASH_FILE);
        writeHashToFile(hashFile, calculateCurrentHash());
        saveAssetsToCache();
    }

    private void writeHashToFile(File file, String hash) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(hash);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveAssetsToCache() {
        File cacheDir = new File(CACHE_DIR);
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }

        for (Map.Entry<String, byte[]> entry : assetCache.entrySet()) {
            File file = new File(cacheDir, entry.getKey());
            try {
                Files.write(file.toPath(), entry.getValue());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void clearCache() {
        File cacheDir = new File(CACHE_DIR);
        if (cacheDir.exists() && cacheDir.isDirectory()) {
            for (File file : cacheDir.listFiles()) {
                file.delete();
            }
        }
    }

    public void cacheAsset(String key, byte[] data) {
        assetCache.put(key, data);
    }

    public byte[] getAsset(String key) {
        return assetCache.get(key);
    }

    public void invalidateCache() {
        clearCache();
        saveCache();
    }

    public void shutdown() {
        saveCache();
    }
}
