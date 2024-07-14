package nr.tpa.command;

import nr.FPC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

//tpa
public class TPAC implements CommandExecutor {

    private final FPC plugin;

    public TPAC(FPC plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("Only players can use this command");
            return true;
        }
        Player player = (Player) commandSender;
        Player target = plugin.getServer().getPlayer(strings[0]);

        if (strings.length != 1) {
            commandSender.sendMessage("Usage: /tpa <player>");
            return true;
        }

        if (target == null || !target.isOnline()) {
            player.sendMessage("Player not found or not online");
            return true;
        }

        if (player == target) {
            player.sendMessage("You can't teleport to yourself");
            return true;
        }

        if (plugin.getTpaRequests().containsKey(player)) {
            player.sendMessage("You already have a pending request");
            return true;
        }

        plugin.getTpaRequests().put(player, target);
        plugin.getTpaRequestTimes().put(player, System.currentTimeMillis());

        player.sendMessage("Request sent to " + target.getName());
        target.sendMessage(player.getName() + " has requested to teleport to you. \nType /tpaccept to accept | /tpadeny to deny");

        return true;
    }
}
