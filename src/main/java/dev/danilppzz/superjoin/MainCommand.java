package dev.danilppzz.superjoin;

import dev.danilppzz.superjoin.common.HexColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MainCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String string, String[] args) {
        if (args[0].equalsIgnoreCase("reload") && hasPermission(sender, "superjoin.reload")) {
            SuperJoin.getInstance().reloadConfig();
            sender.sendMessage(HexColor.write("&8[#5D9CD6SUPERJOIN&8] "+SuperJoin.getInstance().getConfig().getString("translation.reload_command")));
        }
        return false;
    }

    private boolean hasPermission(CommandSender sender, String permission) {
        if (sender.hasPermission(permission)) {
            return true;
        } else {
            sender.sendMessage(HexColor.write("&8[&cSUPERJOIN&8] "+SuperJoin.getInstance().getConfig().getString("translation.error_permission")));
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
