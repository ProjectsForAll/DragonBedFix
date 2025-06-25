package host.plas.dragonbedfix.data;

import host.plas.bou.scheduling.BaseDelayedRunnable;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ClickedTimer extends BaseDelayedRunnable {
    private ClickedBed clickedBed;

    public ClickedTimer(ClickedBed clickedBed) {
        super(60L);

        this.clickedBed = clickedBed;
    }

    @Override
    public void runDelayed() {
        clickedBed.remove();
    }
}
