package nr.fly;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class FlyCommand implements CommandExecutor {

    private ArrayList<Player> list_of_flying_players = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player){
            Player player = (Player) commandSender;
            if(strings.length == 0){
                flyMethod(player);
            }else if(strings.length == 1) {
                Player target = Bukkit.getPlayer(strings[0]);
                flyMethod(target);
            }
        }
        return true;
    }

    private void flyMethod(Player player) {
        if (player.hasPermission("fpc.vip")) {
            if (list_of_flying_players.contains(player)) {
                list_of_flying_players.remove(player);
                player.setAllowFlight(false);
                player.sendMessage(ChatColor.RED + "你不能飛ㄏㄏ");
            } else if (!list_of_flying_players.contains(player)) {
                list_of_flying_players.add(player);
                player.setAllowFlight(true);
                player.sendMessage(ChatColor.YELLOW + "你可以飛OMG");
            }
        }
    }
}
