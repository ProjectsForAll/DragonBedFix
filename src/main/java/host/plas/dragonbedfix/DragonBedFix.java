package host.plas.dragonbedfix;

import host.plas.bou.BetterPlugin;
import host.plas.dragonbedfix.config.MainConfig;
import host.plas.dragonbedfix.data.ClickedBeds;
import host.plas.dragonbedfix.events.MainListener;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public final class DragonBedFix extends BetterPlugin {
    @Getter @Setter
    private static DragonBedFix instance;
    @Getter @Setter
    private static MainConfig mainConfig;

    @Getter @Setter
    private static MainListener mainListener;

    public DragonBedFix() {
        super();
    }

    @Override
    public void onBaseEnabled() {
        // Plugin startup logic
        setInstance(this);

        setMainConfig(new MainConfig());

        setMainListener(new MainListener());
    }

    @Override
    public void onBaseDisable() {
        // Plugin shutdown logic
        ClickedBeds.getClickedBeds().forEach(clickedBed -> {
            clickedBed.getTimer().cancel();
            clickedBed.remove();
        });
    }
}
