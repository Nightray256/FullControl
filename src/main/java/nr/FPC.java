package nr;

import nr.fly.FlyCommand;
import nr.lockchest.command.LCCommand;
import nr.lockchest.listener.LockChest;
import nr.pvp.PVPCommand;
import nr.rtp.RTPCommand;
import nr.tpa.TPACommand;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class FPC extends JavaPlugin {

    @Override
    public void onEnable() {
        this.getCommand("fly").setExecutor(new FlyCommand());
        this.getCommand("pvp").setExecutor(new PVPCommand());
        this.getCommand("rtp").setExecutor(new RTPCommand());
        this.getCommand("lockchest").setExecutor(new LCCommand());
        this.getCommand("tpa").setExecutor(new TPACommand());

        getServer().getPluginManager().registerEvents(new LockChest(), this);
    }
}
