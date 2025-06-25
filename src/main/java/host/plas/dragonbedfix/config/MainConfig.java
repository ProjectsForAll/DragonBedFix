package host.plas.dragonbedfix.config;

import gg.drak.thebase.storage.resources.flat.simple.SimpleConfiguration;
import host.plas.dragonbedfix.DragonBedFix;

public class MainConfig extends SimpleConfiguration {
    public MainConfig() {
        super("config.yml", DragonBedFix.getInstance(), true);
    }

    @Override
    public void init() {
        isBroadcastKill();
    }

    public boolean isBroadcastKill() {
        reloadResource();

        return getOrSetDefault("broadcast-kill", false);
    }
}
