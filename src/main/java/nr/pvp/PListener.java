package nr.pvp;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.Set;

public class PListener implements Listener {

    private final Set<Player> pvpPlayers;
    public PListener(Set<Player> pvpPlayers) {
        this.pvpPlayers = pvpPlayers;
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
            Player victim = (Player) event.getEntity();
            Player attacker = (Player) event.getDamager();

            if (!pvpPlayers.contains(victim)) {
                attacker.sendMessage(ChatColor.RED + "該玩家已關閉PvP！");
                event.setCancelled(true);
            }
        }
    }
}
