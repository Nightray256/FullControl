package nr.miner.command;

import nr.FPC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MineLog implements CommandExecutor {

    private final FPC plugin;
    public MineLog(FPC plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (commandSender instanceof Player){
            Player player = (Player) commandSender;
            plugin.toggleLogMiner(player.getUniqueId());
            player.sendMessage("Miner for logs " + (plugin.isLogMiner(player.getUniqueId()) ? "ON" : "OFF"));
        } else {
            commandSender.sendMessage("Only players can use this command");
        }
        return true;
    }
}
