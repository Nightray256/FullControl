package nr.tpa;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TPACommand implements CommandExecutor {

    private final TPAManager TM = new TPAManager();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(ChatColor.RED + "只有玩家可以使用此命令！");
            return true;
        }

        Player player = (Player) commandSender;

        if (strings.length != 1) {
            player.sendMessage(ChatColor.RED + "用法: /tpa <玩家ID>");
            return true;
        }

        Player target = Bukkit.getPlayer(strings[0]);

        if (target == null || !target.isOnline()) {
            player.sendMessage(ChatColor.RED + "玩家未在線或不存在！");
            return true;
        }

        if (target.equals(player)) {
            player.sendMessage(ChatColor.RED + "你不能傳送到自己！");
            return true;
        }

        TM.sendRequest(player, target);
        player.sendMessage(ChatColor.GREEN + "傳送請求已發送給 " + target.getName());
        target.sendMessage(ChatColor.YELLOW + "你收到來自 " + player.getName() + " 的傳送申請。");
        target.sendMessage(ChatColor.GREEN + "同意的話輸入 /tpaccept 或點擊 " + ChatColor.BOLD + "[同意]");
        target.sendMessage(ChatColor.RED + "不同意的話輸入 /tpadeny 或點擊 " + ChatColor.BOLD + "[不同意]");

        return true;
    }
}
