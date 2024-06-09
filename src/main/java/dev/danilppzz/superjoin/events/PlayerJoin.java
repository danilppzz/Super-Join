package dev.danilppzz.superjoin.events;

import dev.danilppzz.superjoin.common.HexColor;
import dev.danilppzz.superjoin.common.player.PlayerHead;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class PlayerJoin implements Listener {

    public static String imageToText(BufferedImage image, Player player) {
        String playerName = player.getName();
        StringBuilder result = new StringBuilder();
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                Color pixelColor = new Color(image.getRGB(x, y));
                int red = pixelColor.getRed();
                int green = pixelColor.getGreen();
                int blue = pixelColor.getBlue();

                if (x == 0) {
                    String coloredSymbol = String.format(" #%02X%02X%02X⬛", red, green, blue);
                    result.append(HexColor.write(coloredSymbol));
                } else {
                    if (x == 7 && y == 1) {
                        String coloredSymbol = String.format("#%02X%02X%02X⬛   &fWelcome %s to the server!", red, green, blue, playerName);
                        result.append(HexColor.write(coloredSymbol));
                    } else {
                        String coloredSymbol = String.format("#%02X%02X%02X⬛", red, green, blue);
                        result.append(HexColor.write(coloredSymbol));
                    }
                }
            }
            result.append("\n");
        }
        return result.toString();
    }


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) throws IOException {
        event.setJoinMessage(null);
        Player player = event.getPlayer();

        BufferedImage image = PlayerHead.get(player.getUniqueId().toString());
        event.getPlayer().sendMessage(HexColor.write("&f "));
        event.getPlayer().sendMessage(imageToText(image, player));
        event.getPlayer().sendMessage(HexColor.write("&f "));
    }

    @EventHandler
    public void onSendMessage(AsyncPlayerChatEvent event) throws IOException {
        event.setCancelled(true);
        event.getPlayer().sendMessage(HexColor.write(event.getMessage()));
    }
}
