package nr.pvp;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Set;

public class PVPCommand implements CommandExecutor {

    private final Set<Player> pvpPlayers;

    public PVPCommand(Set<Player> pvpPlayers) {
        this.pvpPlayers = pvpPlayers;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(ChatColor.RED + "只有玩家可以使用此命令！");
            return true;
        }

        Player player = (Player) commandSender;

        if (strings.length > 0) {
            player.sendMessage(ChatColor.RED + "此命令不需要任何參數。");
            return true;
        }

        togglePvp(player);
        return true;
    }

    private void togglePvp(Player player) {
        if (pvpPlayers.contains(player)) {
            pvpPlayers.remove(player);
            player.sendMessage(ChatColor.GREEN + "PvP已關閉！");
        } else {
            pvpPlayers.add(player);
            player.sendMessage(ChatColor.GREEN + "PvP已開啟！");
        }
    }
}
