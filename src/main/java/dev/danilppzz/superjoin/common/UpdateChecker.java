package dev.danilppzz.superjoin.common;

import dev.danilppzz.superjoin.SuperJoin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class UpdateChecker {
    public static void check() {
        try {
            // Get version of Spigot API.
            HttpURLConnection connection = (HttpURLConnection) (new URL("https://api.spigotmc.org/legacy/update.php?resource=96161")).openConnection();
            int timed_out = 1250;

            connection.setConnectTimeout(timed_out);
            connection.setReadTimeout(timed_out);

            String latestVersion = (new BufferedReader(new InputStreamReader(connection.getInputStream()))).readLine();

            // Convert strings to number.
            int latestVersionNumbers = Integer.parseInt(latestVersion.replaceAll("\\.", ""));
            int pluginVersion = Integer.parseInt(SuperJoin.getInstance().getDescription().getVersion().replaceAll("\\.", ""));

            // If the plugin is not up-to-date, send a message with the link to update it.
            if (latestVersionNumbers > pluginVersion) {
                SuperJoin.getInstance().getLogger().severe("There is a new version available: "+latestVersion);
                SuperJoin.getInstance().getLogger().severe("You can download it at: https://www.spigotmc.org/resources/perworldplugins.96161/");
            }
        } catch (Exception var3) {
            // Send the error message.
            Bukkit.getConsoleSender().sendMessage(SuperJoin.getInstance().getName() + ChatColor.RED + " Error while checking update.");
        }
    }
}
