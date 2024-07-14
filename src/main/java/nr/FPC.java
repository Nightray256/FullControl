package nr;

import nr.fly.FlyCommand;
import nr.lockchest.command.LCCommand;
import nr.lockchest.listener.LockChest;
import nr.pvp.PVPCommand;
import nr.rtp.RTPCommand;
import nr.tpa.command.*;
import nr.tpa.listener.TimeOut;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class FPC extends JavaPlugin {

    private final Set<Player> pvpPlayers = new HashSet<>();

    private Map<Player, Player> tpaRequests = new HashMap<>();
    private Map<Player, Long> tpaRequestTimes = new HashMap<>();
    private Map<Player, Player> tpahereRequests = new HashMap<>();
    private Map<Player, Long> tpahereRequestTimes = new HashMap<>();
    private final long REQUEST_TIMEOUT = 60000;

    @Override
    public void onEnable() {
        this.getCommand("fly").setExecutor(new FlyCommand());
        this.getCommand("pvp").setExecutor(new PVPCommand(pvpPlayers));
        this.getCommand("rtp").setExecutor(new RTPCommand());
        this.getCommand("lockchest").setExecutor(new LCCommand());
        getCommand("tpa").setExecutor(new TPAC(this));
        getCommand("tpaccept").setExecutor(new ACC(this));
        getCommand("tpadeny").setExecutor(new REJ(this));
        getCommand("tpacancel").setExecutor(new TPACAN(this));
        getCommand("tpahere").setExecutor(new TPAH(this));

        getServer().getPluginManager().registerEvents(new LockChest(), this);

        getServer().getPluginManager().registerEvents(new TimeOut(this), this);

        getServer().getScheduler().runTaskTimer(this, () -> {
            long now = System.currentTimeMillis();
            tpaRequests.entrySet().removeIf(entry -> {
                if (now - tpaRequestTimes.get(entry.getKey()) > getRequestTimeout()) {
                    Player requester = entry.getValue();
                    Player target = entry.getKey();
                    requester.sendMessage("Your teleport request to " + target.getName() + " has timed out.");
                    target.sendMessage("Teleport request from " + requester.getName() + " has timed out.");
                    tpaRequestTimes.remove(target);
                    return true;
                }
                return false;
            });
            tpahereRequests.entrySet().removeIf(entry -> {
                if (now - tpahereRequestTimes.get(entry.getKey()) > getRequestTimeout()) {
                    Player requester = entry.getValue();
                    Player target = entry.getKey();
                    requester.sendMessage("Your teleport request for " + target.getName() + " to come to you has timed out.");
                    target.sendMessage("Teleport request from " + requester.getName() + " has timed out.");
                    tpahereRequestTimes.remove(target);
                    return true;
                }
                return false;
            });
        }, 0L, 20L);
    }

    public Map<Player, Player> getTpaRequests() {
        return tpaRequests;
    }

    public Map<Player, Long> getTpaRequestTimes() {
        return tpaRequestTimes;
    }

    public Map<Player, Player> getTpahereRequests() {
        return tpahereRequests;
    }

    public Map<Player, Long> getTpahereRequestTimes() {
        return tpahereRequestTimes;
    }

    public long getRequestTimeout() {
        return REQUEST_TIMEOUT;
    }
}
