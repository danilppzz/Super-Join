package dev.danilppzz.superjoin.events;

import dev.danilppzz.superjoin.SuperJoin;
import dev.danilppzz.superjoin.common.HexColor;
import dev.danilppzz.superjoin.common.player.PlayerHead;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class PlayerJoin implements Listener {

    public static String imageToText(BufferedImage image) {
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
                    if (x == 7) {
                        String coloredSymbol = "";
                        switch (y) {
                            case 0:
                                coloredSymbol = String.format("#%02X%02X%02X⬛   %s", red, green, blue,
                                        SuperJoin.getInstance().getConfig().getList("join_message.message_content").get(0));
                                result.append(HexColor.write(coloredSymbol));
                                break;
                            case 1:
                                coloredSymbol = String.format("#%02X%02X%02X⬛   %s", red, green, blue,
                                        SuperJoin.getInstance().getConfig().getList("join_message.message_content").get(1));
                                result.append(HexColor.write(coloredSymbol));
                                break;
                            case 2:
                                coloredSymbol = String.format("#%02X%02X%02X⬛   %s", red, green, blue,
                                        SuperJoin.getInstance().getConfig().getList("join_message.message_content").get(2));
                                result.append(HexColor.write(coloredSymbol));
                                break;
                            case 3:
                                coloredSymbol = String.format("#%02X%02X%02X⬛   %s", red, green, blue,
                                        SuperJoin.getInstance().getConfig().getList("join_message.message_content").get(3));
                                result.append(HexColor.write(coloredSymbol));
                                break;
                            case 4:
                                coloredSymbol = String.format("#%02X%02X%02X⬛   %s", red, green, blue,
                                        SuperJoin.getInstance().getConfig().getList("join_message.message_content").get(4));
                                result.append(HexColor.write(coloredSymbol));
                                break;
                            case 5:
                                coloredSymbol = String.format("#%02X%02X%02X⬛   %s", red, green, blue,
                                        SuperJoin.getInstance().getConfig().getList("join_message.message_content").get(5));
                                result.append(HexColor.write(coloredSymbol));
                                break;
                            case 6:
                                coloredSymbol = String.format("#%02X%02X%02X⬛   %s", red, green, blue,
                                        SuperJoin.getInstance().getConfig().getList("join_message.message_content").get(6));
                                result.append(HexColor.write(coloredSymbol));
                                break;
                            case 7:
                                coloredSymbol = String.format("#%02X%02X%02X⬛   %s", red, green, blue,
                                        SuperJoin.getInstance().getConfig().getList("join_message.message_content").get(7));
                                result.append(HexColor.write(coloredSymbol));
                                break;
                        }

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

        if (SuperJoin.getInstance().getConfig().getBoolean("hooks.enabled")) {
            DiscordHook.join(player);
        }

        if (SuperJoin.getInstance().getConfig().getBoolean("join_message.use_sound")) {
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
        }

        if (SuperJoin.getInstance().getConfig().getBoolean("hooks.default_enabled")) {
            WebHook.send(event.getPlayer(), event);
        }

        if (SuperJoin.getInstance().getConfig().getBoolean("join_message.player_head_join_message")) {
            BufferedImage image = PlayerHead.get(player.getUniqueId().toString(), false);
            if (SuperJoin.getInstance().getConfig().getBoolean("join_message.use_margin")) {
                event.getPlayer().sendMessage(HexColor.write("&f "));
                event.getPlayer().sendMessage(PlaceholderAPI.setPlaceholders(player, imageToText(image)));
                event.getPlayer().sendMessage(HexColor.write("&f "));
            } else {
                event.getPlayer().sendMessage(PlaceholderAPI.setPlaceholders(player, imageToText(image)));
            }
        } else {
            String message = SuperJoin.getInstance().getConfig().getString("join_message.default_message");
            if (SuperJoin.getInstance().getConfig().getBoolean("join_message.use_margin")) {
                event.getPlayer().sendMessage(HexColor.write("&f "));
                event.getPlayer().sendMessage(PlaceholderAPI.setPlaceholders(player, HexColor.write("&f " + message)));
                event.getPlayer().sendMessage(HexColor.write("&f "));
            } else {
                event.getPlayer().sendMessage(PlaceholderAPI.setPlaceholders(player, HexColor.write("&f " + message)));
            }
        }
    }
}
