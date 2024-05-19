package nr.rtp;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Random;

public class RTPCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(ChatColor.RED + "只有玩家可以使用此命令！");
            return true;
        }

        Player player = (Player) commandSender;
        Random random = new Random();

        int x, y, z;
        int maxTries = 100;
        int tries = 0;

        do {
            x = random.nextInt(10000) - 5000;
            z = random.nextInt(10000) - 5000;
            y = player.getWorld().getHighestBlockYAt(x, z);

            Block block = player.getWorld().getBlockAt(x, y, z);
            Material material = block.getType();

            if (material == Material.AIR || material == Material.WATER || material == Material.LAVA) {
                player.teleport(block.getLocation().add(0.5, 1, 0.5));
                player.sendMessage(ChatColor.GREEN + "傳送成功！" + ChatColor.GRAY + " (x: " + x + ", y: " + y + ", z: " + z + ")");
                return true;
            }
            tries++;
        } while (y == 0 && tries < maxTries);

        player.sendMessage(ChatColor.RED + "傳送失敗！請再試一次。");

        return true;
    }
}
