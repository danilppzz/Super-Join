package dev.danilppzz.superjoin;

import dev.danilppzz.superjoin.common.DiscordHookConfig;
import dev.danilppzz.superjoin.common.HexColor;
import dev.danilppzz.superjoin.events.DiscordHook;
import dev.danilppzz.superjoin.events.PlayerJoin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MainCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String string, String[] args) {
        if (args[0].equalsIgnoreCase("reload") && hasPermission(sender, "superjoin.reload")) {
            SuperJoin.getInstance().reloadConfig();
            DiscordHookConfig.reload();
            sender.sendMessage(HexColor.write("&8[#FFB600⭐&8] &fThe plugin was reloaded."));
        }
        return false;
    }

    private boolean hasPermission(CommandSender sender, String permission) {
        if (sender.hasPermission(permission)) {
            return true;
        } else {
            sender.sendMessage(HexColor.write("&8[&c❌&8] "+SuperJoin.getInstance().getConfig().getString("translation.error_permission")));
            return false;
        }
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, Command command, @NotNull String label, String[] args) {
        List<String> argList = new ArrayList<>();
        if (command.getName().equalsIgnoreCase("superjoin")) {
            if (args.length == 1) {
                argList.add("reload");
                return argList;
            }
        }
        return null;
    }
}
