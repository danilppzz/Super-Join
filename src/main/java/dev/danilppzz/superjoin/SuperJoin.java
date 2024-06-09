package dev.danilppzz.superjoin;

import dev.danilppzz.superjoin.common.HexColor;
import dev.danilppzz.superjoin.events.PlayerJoin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class SuperJoin extends JavaPlugin {

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

        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);

//        getCommand("chattype").setExecutor(new MainCommands());

        Bukkit.getConsoleSender().sendMessage(HexColor.write("&2Loading configuration..."));
    }


    @Override
    public void onDisable()
    {
        this.reloadConfig();
        this.saveConfig();
    }
}
