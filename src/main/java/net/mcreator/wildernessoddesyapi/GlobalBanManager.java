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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class GlobalBanManager {

    private static final String GITHUB_REPO_OWNER = "your-github-username";
    private static final String GITHUB_REPO_NAME = "anti-cheat-global-bans";
    private static final String GITHUB_TOKEN = "your-personal-access-token";

    private static final String BAN_LIST_FILE = "global-ban-list.json";
    private static final Gson gson = new Gson();

    public static void banPlayerGlobally(String playerUUID, String reason) {
        if (!AntiCheatHandler.antiCheatEnabled) {
            System.out.println("Anti-cheat is disabled. Ban action is not executed.");
            return;
        }

        try {
            Map<String, String> currentBanList = getBanList();
            currentBanList.put(playerUUID, reason);
            updateBanList(currentBanList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void unbanPlayerGlobally(String playerUUID) {
        if (!AntiCheatHandler.antiCheatEnabled) {
            System.out.println("Anti-cheat is disabled. Unban action is not executed.");
            return;
        }

        try {
            Map<String, String> currentBanList = getBanList();
            currentBanList.remove(playerUUID);
            updateBanList(currentBanList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Map<String, String> getBanList() throws IOException {
        // This method can continue as it does not directly depend on the anti-cheat state
        // but should still be careful to handle requests properly.
        String apiUrl = "https://api.github.com/repos/" + GITHUB_REPO_OWNER + "/" + GITHUB_REPO_NAME + "/contents/" + BAN_LIST_FILE;
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "token " + GITHUB_TOKEN);
        connection.setRequestProperty("Accept", "application/vnd.github.v3+json");

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            InputStreamReader reader = new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8);
            Type mapType = new TypeToken<Map<String, String>>() {}.getType();
            return gson.fromJson(reader, mapType);
        } else {
            return new HashMap<>();
        }
    }

    private static void updateBanList(Map<String, String> banList) throws IOException {
        String apiUrl = "https://api.github.com/repos/" + GITHUB_REPO_OWNER + "/" + GITHUB_REPO_NAME + "/contents/" + BAN_LIST_FILE;
        String content = gson.toJson(banList);
        String encodedContent = Base64.getEncoder().encodeToString(content.getBytes(StandardCharsets.UTF_8));
        String message = "Updating global ban list";

        String jsonBody = "{"
                + "\"message\": \"" + message + "\","
                + "\"content\": \"" + encodedContent + "\""
                + "}";

        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("PUT");
        connection.setRequestProperty("Authorization", "token " + GITHUB_TOKEN);
        connection.setRequestProperty("Accept", "application/vnd.github.v3+json");
        connection.setDoOutput(true);

        try (OutputStream os = connection.getOutputStream()) {
            os.write(jsonBody.getBytes(StandardCharsets.UTF_8));
        }
    }
}
