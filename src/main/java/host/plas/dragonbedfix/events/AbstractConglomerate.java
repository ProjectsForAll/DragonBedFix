package host.plas.dragonbedfix.events;

import gg.drak.thebase.events.BaseEventHandler;
import host.plas.bou.events.ListenerConglomerate;
import host.plas.dragonbedfix.DragonBedFix;
import org.bukkit.Bukkit;

public class AbstractConglomerate implements ListenerConglomerate {
    public AbstractConglomerate() {
        register();
    }

    public void register() {
        Bukkit.getPluginManager().registerEvents(this, DragonBedFix.getInstance());
        BaseEventHandler.bake(this, DragonBedFix.getInstance());
        DragonBedFix.getInstance().logInfo("Registered listeners for: &c" + this.getClass().getSimpleName());
    }
}
