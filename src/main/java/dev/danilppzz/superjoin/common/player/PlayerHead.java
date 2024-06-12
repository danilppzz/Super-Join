package dev.danilppzz.superjoin.common.player;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dev.danilppzz.superjoin.SuperJoin;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.Base64;
import java.net.URL;
import java.util.UUID;

public class PlayerHead {

    private static BufferedImage apply(UUID uuid, BufferedImage in) {
        BufferedImage head;

        BufferedImage layer1 = in.getSubimage(8, 8, 8, 8);
        BufferedImage layer2 = in.getSubimage(40, 8, 8, 8);

        try {
            head = new BufferedImage(8,  8, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = head.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
            g.drawImage(layer1, 0, 0, 8, 8, null);
            g.drawImage(layer2, 0, 0, 8, 8, null);
        } catch (Throwable t) { // There might be problems with headless servers when loading the graphics class, so we catch every exception and error on purpose here
            head = new BufferedImage(8, 8, in.getType());
            layer1.copyData(head.getRaster());
        }

        return head;
    }

    private static BufferedImage applyHeadIcon(UUID uuid, BufferedImage in) {
        BufferedImage head;

        BufferedImage layer1 = in.getSubimage(8, 8, 8, 8);
        BufferedImage layer2 = in.getSubimage(40, 8, 8, 8);

        try {
            head = new BufferedImage(48,  48, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = head.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
            g.drawImage(layer1, 4, 4, 40, 40, null);
            g.drawImage(layer2, 0, 0, 48, 48, null);
        } catch (Throwable t) { // There might be problems with headless servers when loading the graphics class, so we catch every exception and error on purpose here
            head = new BufferedImage(8, 8, in.getType());
            layer1.copyData(head.getRaster());
        }

        return head;
    }

    public static BufferedImage get(String uuid, Boolean headIcon) throws IOException {
        String profileUrl = "https://sessionserver.mojang.com/session/minecraft/profile/" + uuid;
        HttpURLConnection connection = (HttpURLConnection) new URL(profileUrl).openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            Gson gson = new Gson();
            String base64Textures = gson.fromJson(response.toString(), JsonObject.class)
                    .getAsJsonArray("properties").get(0)
                    .getAsJsonObject().get("value").getAsString();
            String jsonTextures = new String(Base64.getDecoder().decode(base64Textures));

            String skinUrl = gson.fromJson(jsonTextures, JsonObject.class)
                    .getAsJsonObject("textures").getAsJsonObject("SKIN")
                    .get("url").getAsString();

            if (headIcon) {
                return applyHeadIcon(UUID.fromString(uuid), ImageIO.read(new URL(skinUrl)));
            } else {
                return apply(UUID.fromString(uuid), ImageIO.read(new URL(skinUrl)));
            }
        } else {
            return apply(UUID.fromString(uuid), ImageIO.read(new URL("https://github.com/danilppzz/Super-Join/blob/main/resources/steve.png")));
        }
    }
}
