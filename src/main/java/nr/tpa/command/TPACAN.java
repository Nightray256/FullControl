package nr.tpa.command;

import nr.FPC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

//cancel
public class TPACAN implements CommandExecutor {

    private FPC plugin;
    public TPACAN(FPC plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("Only players can use this command");
            return true;
        }

        Player player = (Player) commandSender;
        boolean requestCancelled = false;

        if (strings.length != 1) {
            commandSender.sendMessage("Usage: /tpacancel");
            return true;
        }

        for (Map.Entry<Player, Player> entry : plugin.getTpaRequests().entrySet()) {
            if (entry.getValue() == player) {
                Player target = entry.getKey();
                plugin.getTpaRequests().remove(entry.getKey());
                plugin.getTpaRequestTimes().remove(entry.getKey());

                player.sendMessage("Request cancelled");
                target.sendMessage(player.getName() + " has cancelled the request");
                requestCancelled = true;
                break;
            }
        }

        if (!requestCancelled) {
            for (Map.Entry<Player, Player> entry : plugin.getTpahereRequests().entrySet()) {
                if (entry.getValue() == player) {
                    Player target = entry.getKey();
                    plugin.getTpahereRequests().remove(entry.getKey());
                    plugin.getTpahereRequestTimes().remove(entry.getKey());

                    player.sendMessage("Request cancelled");
                    target.sendMessage(player.getName() + " has cancelled the request");
                    break;
                }
            }
        }

        if (!requestCancelled) {
            player.sendMessage("No pending requests");
        }
        return true;
    }
}
