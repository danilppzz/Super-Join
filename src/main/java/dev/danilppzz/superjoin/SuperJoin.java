package dev.danilppzz.superjoin;

import dev.danilppzz.superjoin.common.HexColor;
import dev.danilppzz.superjoin.common.UpdateChecker;
import dev.danilppzz.superjoin.events.PlayerJoin;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class SuperJoin extends JavaPlugin implements Listener {

    private static SuperJoin instance;

    public static SuperJoin getInstance()
    {
        return instance;
    }

    @Override
    public void onEnable()
    {
        instance = this;
        saveDefaultConfig();

        // Dependencies Update
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            Bukkit.getPluginManager().registerEvents(this, this);
        } else {
            getInstance().getLogger().severe("&8[&2SUPERJOIN&8] Could not find PlaceholderAPI! This plugin is required.");
            Bukkit.getPluginManager().disablePlugin(this);
        }

        // Check updates.
        if (getConfig().getBoolean("updateChecker")) UpdateChecker.check();

        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
        getCommand("superjoin").setExecutor(new MainCommand());

        Bukkit.getConsoleSender().sendMessage(HexColor.write("&8[&2SUPERJOIN&8] "+getConfig().getString("translation.loading_config")));
    }

    @Override
    public void onDisable()
    {
        this.reloadConfig();
        this.saveConfig();
    }
}
