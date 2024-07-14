package nr.tpa.command;

import nr.FPC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

//accept
public class ACC implements CommandExecutor {

    private final FPC plugin;

    public ACC(FPC plugin) {
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
            commandSender.sendMessage("Usage: /tpaccept");
            return true;
        }

        if (plugin.getTpaRequests().containsKey(player)) {
            Player requester = plugin.getTpaRequests().remove(player);
            plugin.getTpaRequestTimes().remove(player);
            requester.teleport(player.getLocation());
            player.sendMessage("You accepted the teleport request.");
            requester.sendMessage("Your teleport request was accepted.");
        } else if (plugin.getTpahereRequests().containsKey(player)) {
            Player requester = plugin.getTpahereRequests().remove(player);
            plugin.getTpahereRequestTimes().remove(player);
            player.teleport(requester.getLocation());
            player.sendMessage("You accepted the teleport request.");
            requester.sendMessage("Your teleport request was accepted.");
        } else {
            player.sendMessage("No pending requests");
        }

        return true;
    }
}
