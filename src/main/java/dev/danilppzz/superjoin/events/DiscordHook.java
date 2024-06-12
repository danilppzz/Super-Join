package dev.danilppzz.superjoin.events;

import dev.danilppzz.superjoin.SuperJoin;
import dev.danilppzz.superjoin.common.ImageUtils;
import dev.danilppzz.superjoin.common.player.PlayerHead;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DiscordHook {

    private static final String WEBHOOK_URL = SuperJoin.getInstance().getConfig().getString("hooks.discord_hook_url");
    public static void join(Player player) {
        try {
            File jsonFile = new File(SuperJoin.getInstance().getDataFolder(), "hook/discord.json");
            String jsonPayload = readJsonFromFile(jsonFile);

            jsonPayload = PlaceholderAPI.setPlaceholders(player, jsonPayload);

            if (jsonPayload.contains("\"url\": \"<icon>\"") && !SuperJoin.useBetaFeatures()) {
                BufferedImage image = PlayerHead.get(player.getUniqueId().toString(), true);
                String playerHeadBase64 = ImageUtils.convertBufferedImageToBase64(image);
                String playerHeadUrl = "data:image/png;base64," + playerHeadBase64;
                jsonPayload = jsonPayload.replace("\"url\": \"<icon>\"", "\"url\": \"" + playerHeadUrl + "\"");
            }

            URL url = new URL(WEBHOOK_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonPayload.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                SuperJoin.getInstance().getLogger().severe(String.valueOf(responseCode));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String readJsonFromFile(File file) throws Exception {
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                contentBuilder.append(sCurrentLine);
            }
        }
        return contentBuilder.toString();
    }
}
