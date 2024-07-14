package nr.tpa.command;

import nr.FPC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

//deny
public class REJ implements CommandExecutor {

    private FPC plugin;
    public REJ(FPC plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("Only players can use this command");
            return true;
        }
        Player player = (Player) commandSender;
        if (strings.length != 1) {
            commandSender.sendMessage("Usage: /tpadeny");
            return true;
        }

        if (plugin.getTpaRequests().containsKey(player)) {
            Player requester = plugin.getTpaRequests().remove(player);
            plugin.getTpaRequestTimes().remove(player);
            player.sendMessage("You denied the teleport request.");
            requester.sendMessage("Your teleport request was denied.");
        } else if (plugin.getTpahereRequests().containsKey(player)) {
            Player requester = plugin.getTpahereRequests().remove(player);
            plugin.getTpahereRequestTimes().remove(player);
            player.sendMessage("You denied the teleport request.");
            requester.sendMessage("Your teleport request was denied.");
        } else {
            player.sendMessage("No pending requests");
        }

        return true;
    }
}

