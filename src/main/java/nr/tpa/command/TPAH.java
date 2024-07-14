package nr.tpa.command;

import nr.FPC;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

//tpahere
public class TPAH implements CommandExecutor {

    private FPC plugin;
    public TPAH(FPC plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("Only players can use this command");
            return true;
        }

        if (strings.length != 1) {
            commandSender.sendMessage("Usage: /tpahere <player>");
            return true;
        }

        Player player = (Player) commandSender;
        Player target = Bukkit.getPlayer(strings[0]);

        if (target == null || !target.isOnline()) {
            player.sendMessage("Player not found or not online");
            return true;
        }

        if (plugin.getTpahereRequests().containsKey(target)) {
            player.sendMessage("You already have a pending request");
            return true;
        }

        plugin.getTpahereRequests().put(target, player);
        plugin.getTpahereRequestTimes().put(target, System.currentTimeMillis());
        target.sendMessage(player.getName() + " wants you to teleport to them. \nType /tpaccept to accept | /tpadeny to deny");
        player.sendMessage("Request sent to " + target.getName());

        return true;
    }
}
