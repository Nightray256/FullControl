package nr.lockchest.command;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LCCommand implements CommandExecutor {

    private Map<Block, Map<UUID, String>> chestPermissions = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(ChatColor.RED + "Only players can use this command.");
            return true;
        }

        Player player = (Player) commandSender;
        if (strings.length < 2) {
            player.sendMessage(ChatColor.RED + "Usage: /lockchest add <ID> OR /lockchest remove <ID>");
            return true;
        }

        String subCommand = strings[0];
        String friendID = strings[1];
        Block clickedBlock = player.getTargetBlock(null, 5);

        if (clickedBlock == null || !(clickedBlock.getState() instanceof org.bukkit.block.Sign)) {
            player.sendMessage(ChatColor.RED + "You are not looking at a sign.");
            return true;
        }

        switch (subCommand.toLowerCase()) {
            case "add":
                addFriendPermission(clickedBlock, player.getUniqueId(), friendID);
                player.sendMessage(ChatColor.GREEN + "Friend added to chest permissions.");
                break;
            case "remove":
                removeFriendPermission(clickedBlock, player.getUniqueId(), friendID);
                player.sendMessage(ChatColor.GREEN + "Friend removed from chest permissions.");
                break;
            default:
                player.sendMessage(ChatColor.RED + "Invalid sub-command. Use '/lockchest add <ID>' or '/lockchest remove <ID>'.");
                break;
        }

        return true;
    }

    private void addFriendPermission(Block block, UUID ownerUUID, String friendID) {
        chestPermissions.computeIfAbsent(block, k -> new HashMap<>());
        chestPermissions.get(block).put(ownerUUID, friendID);
    }

    private void removeFriendPermission(Block block, UUID ownerUUID, String friendID) {
        if (chestPermissions.containsKey(block)) {
            Map<UUID, String> permissions = chestPermissions.get(block);
            if (permissions.containsKey(ownerUUID) && permissions.get(ownerUUID).equals(friendID)) {
                permissions.remove(ownerUUID);
            }
        }
    }
}