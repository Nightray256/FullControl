package nr;

import nr.fly.FlyCommand;
import nr.lockchest.command.LCCommand;
import nr.lockchest.listener.LockChest;
import nr.pvp.PVPCommand;
import nr.rtp.RTPCommand;
import nr.tpa.TPACommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;

public final class FPC extends JavaPlugin {

    private final Set<Player> pvpPlayers = new HashSet<>();

    @Override
    public void onEnable() {
        this.getCommand("fly").setExecutor(new FlyCommand());
        this.getCommand("pvp").setExecutor(new PVPCommand(pvpPlayers));
        this.getCommand("rtp").setExecutor(new RTPCommand());
        this.getCommand("lockchest").setExecutor(new LCCommand());
        this.getCommand("tpa").setExecutor(new TPACommand());

        getServer().getPluginManager().registerEvents(new LockChest(), this);
    }
}
