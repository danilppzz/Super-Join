package dev.danilppzz.superjoin.events;

import com.google.gson.Gson;
import dev.danilppzz.superjoin.SuperJoin;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class WebHook {

    private static final String WEBHOOK_URL = SuperJoin.getInstance().getConfig().getString("hooks.default_web_hook");

    private static final Gson gson = new Gson();
    public static void send(Player player, PlayerJoinEvent event) {
        DataJSON data = new DataJSON(player, event);
        String json = gson.toJson(data);
        sendPostRequest(json);
    }

    private static void sendPostRequest(String json) {
        try {
            assert WebHook.WEBHOOK_URL != null;
            URL url = new URL(WebHook.WEBHOOK_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = json.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println("POST request worked.");
            } else {
                System.out.println("POST request failed: " + responseCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("FieldCanBeLocal")
    private static class DataJSON {
        private final Player player;
        private final String event_name;

        public DataJSON(Player player, PlayerJoinEvent event) {
            this.player = player;
            this.event_name = event.getEventName();
        }
    }
}
