package nr.miner.listener;

import nr.FPC;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.Location;

import java.util.UUID;

public class BlockBreaker implements Listener {

    private final FPC plugin;
    public BlockBreaker(FPC plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        UUID playerUUID = event.getPlayer().getUniqueId();

        if (plugin.isChainMiner(playerUUID)) {
            handleChainMining(event);
        }
        if (plugin.isLogMiner(playerUUID)) {
            handleLogMining(event);
        }
    }

    private void handleChainMining(BlockBreakEvent event) {
        Material blockType = event.getBlock().getType();
        if (isMineral(blockType)) {
            breakAdjacentBlocks(event.getBlock().getLocation(), blockType);
        }
    }

    private void handleLogMining(BlockBreakEvent event) {
        Material blockType = event.getBlock().getType();
        if (isLog(blockType)){
            breakAdjacentBlocks(event.getBlock().getLocation(), blockType);
        }
    }

    private boolean isMineral(Material material) {
        switch (material) {
            case DIAMOND_ORE:
            case COAL_ORE:
            case IRON_ORE:
            case GOLD_ORE:
            case LAPIS_ORE:
            case REDSTONE_ORE:
            case EMERALD_ORE:
            case NETHER_QUARTZ_ORE:
                return true;
            default:
                return false;
        }
    }

    private boolean isLog(Material blockType) {
        return blockType.name().endsWith("_LOG");
    }

    private void breakAdjacentBlocks(Location location, Material material) {
        Block block = location.getBlock();
        if (block.getType() == material) {
            block.breakNaturally();
            for (BlockFace face : BlockFace.values()) {
                if (face != BlockFace.SELF) {
                    breakAdjacentBlocks(block.getRelative(face).getLocation(), material);
                }
            }
        }
    }

}
