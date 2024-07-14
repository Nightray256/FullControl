package nr.tpa.listener;

import nr.FPC;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class TimeOut implements Listener {

    private final FPC plugin;
    public TimeOut(FPC plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (plugin.getTpaRequests().containsKey(player)) {
            Player requester = plugin.getTpaRequests().remove(player);
            plugin.getTpaRequestTimes().remove(player);
            requester.sendMessage("The teleport request from + player.getName() + has been cancelled because they logged out.");
        }
        if (plugin.getTpahereRequests().containsKey(player)) {
            Player requester = plugin.getTpahereRequests().remove(player);
            plugin.getTpahereRequestTimes().remove(player);
            requester.sendMessage("The teleport request from + player.getName() + has been cancelled because they logged out.");
        }
    }
}
