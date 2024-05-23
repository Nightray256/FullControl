package nr.tpa;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TPAManager {
    private final Map<UUID, UUID> tpaRequests = new HashMap<>();

    public void sendRequest(Player sender, Player target) {
        tpaRequests.put(target.getUniqueId(), sender.getUniqueId());
    }

    public UUID getRequest(Player target) {
        return tpaRequests.get(target.getUniqueId());
    }

    public void removeRequest(Player target) {
        tpaRequests.remove(target.getUniqueId());
    }

    public boolean hasRequest(Player target) {
        return tpaRequests.containsKey(target.getUniqueId());
    }
}
