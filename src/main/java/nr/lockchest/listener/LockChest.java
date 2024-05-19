package nr.lockchest.listener;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LockChest extends JavaPlugin implements Listener {

    private Map<Block, UUID> lockedChests = new HashMap<>();

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block clickedBlock = event.getClickedBlock();

        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && clickedBlock !=null){
            if (clickedBlock.getType() == Material.CHEST || clickedBlock.getType() == Material.TRAPPED_CHEST || clickedBlock.getType() == Material.OAK_DOOR || clickedBlock.getType() == Material.IRON_DOOR){
                UUID playerUUID = player.getUniqueId();
                lockedChests.put(clickedBlock, playerUUID);
                Block signBlock = clickedBlock.getLocation().add(0, 1, 0).getBlock();
            }
        }
    }
}
