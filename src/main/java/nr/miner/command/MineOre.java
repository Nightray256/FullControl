package nr.miner.command;

import nr.FPC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MineOre implements CommandExecutor {

    private final FPC plugin;
    public MineOre(FPC plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (commandSender instanceof Player){
            Player player = (Player) commandSender;
            plugin.toggleChainMiner(player.getUniqueId());
            player.sendMessage("Miner for ores " + (plugin.isChainMiner(player.getUniqueId()) ? "ON" : "OFF"));
        } else {
            commandSender.sendMessage("Only players can use this command");
        }
        return true;
    }
}
