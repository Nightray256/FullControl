package nr.lockchest.listener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
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
            if (clickedBlock.getType() == Material.CHEST || clickedBlock.getType() == Material.TRAPPED_CHEST
                    || clickedBlock.getType() == Material.OAK_DOOR || clickedBlock.getType() == Material.IRON_DOOR){
                UUID playerUUID = player.getUniqueId();
                lockedChests.put(clickedBlock, playerUUID);
                Block signBlock = clickedBlock.getLocation().add(0, 1, 0).getBlock();

                Sign sign = (Sign) signBlock.getState();
                sign.setLine(0, ChatColor.RED + "[Owner]");
                sign.setLine(1, player.getName());
                sign.update();

                player.sendMessage(ChatColor.GOLD + "Chest has been locked successfully!");
            } else if (lockedChests.containsKey(clickedBlock)){
                UUID ownerUUID = lockedChests.get(clickedBlock);
                if (player.getUniqueId().equals(ownerUUID)){
                    player.sendMessage(ChatColor.RED + "This chest is locked!");
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block brokenBlock = event.getBlock();

        if (brokenBlock.getType() == Material.CHEST || brokenBlock.getType() == Material.TRAPPED_CHEST
                || brokenBlock.getType() == Material.OAK_DOOR || brokenBlock.getType() == Material.IRON_DOOR) {
            if (lockedChests.containsKey(brokenBlock)) {
                UUID ownerUUID = lockedChests.get(brokenBlock);
                if (!player.getUniqueId().equals(ownerUUID)) {
                    player.sendMessage("You cannot break this locked box/door!");
                    event.setCancelled(true);
                }
            }
        } else if (brokenBlock.getType() == Material.OAK_SIGN || brokenBlock.getType() == Material.SPRUCE_SIGN
                || brokenBlock.getType() == Material.BIRCH_SIGN || brokenBlock.getType() == Material.JUNGLE_SIGN
                || brokenBlock.getType() == Material.ACACIA_SIGN || brokenBlock.getType() == Material.DARK_OAK_SIGN) {
            Block attachedBlock = brokenBlock.getRelative(BlockFace.DOWN);
            if (lockedChests.containsKey(attachedBlock)) {
                UUID ownerUUID = lockedChests.get(attachedBlock);
                if (!player.getUniqueId().equals(ownerUUID)) {
                    player.sendMessage("You cannot break this locked sign!");
                    event.setCancelled(true);
                }
            }
        }
    }
}
