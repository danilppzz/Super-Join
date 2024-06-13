package dev.danilppzz.superjoin.configuration;

import dev.danilppzz.superjoin.SuperJoin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

// groups.yml config file.

public class DiscordHookConfig {
    private static FileConfiguration list = null;
    private static File listFile = null;

    public static FileConfiguration get() {
        if (list == null) reload();
        return list;
    }

    public static void reload() {
        if (list == null) listFile = new File(SuperJoin.getInstance().getDataFolder(), "hook/discord.json");

        list = YamlConfiguration.loadConfiguration(listFile);
        Reader defConfigStream = new InputStreamReader(Objects.requireNonNull(SuperJoin.getInstance().getResource("hook/discord.json")), StandardCharsets.UTF_8);
        list.setDefaults(YamlConfiguration.loadConfiguration(defConfigStream));
    }

    public static void save() {
        try {
            list.save(listFile);
        } catch (IOException var2) {
            var2.printStackTrace();
        }

    }

    public static void register() {
        listFile = new File(SuperJoin.getInstance().getDataFolder(), "hook/discord.json");

        if (!listFile.exists()) {
            get().options().copyDefaults(true);
            save();
        }
    }
}
