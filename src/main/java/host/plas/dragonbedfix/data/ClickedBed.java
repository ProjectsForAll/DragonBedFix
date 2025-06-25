package host.plas.dragonbedfix.data;

import gg.drak.thebase.objects.Identifiable;
import host.plas.bou.commands.Sender;
import host.plas.bou.utils.SenderUtils;
import host.plas.dragonbedfix.utils.AdvancementUtils;
import host.plas.dragonbedfix.utils.LocationUtils;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.block.Block;

import java.util.List;
import java.util.Optional;

@Getter @Setter
public class ClickedBed implements Identifiable {
    private String identifier;

    private List<Block> bedBlocks;
    private String playerUuid;

    private ClickedTimer timer;

    public ClickedBed(List<Block> bedBlocks, String playerUuid) {
        this.bedBlocks = bedBlocks;
        this.playerUuid = playerUuid;

        this.identifier = LocationUtils.locationToString(getLocation());

        init();
    }

    public void init() {
        add();
        makeTimer();
    }

    public void makeTimer() {
        this.timer = new ClickedTimer(this);
    }

    public void add() {
        ClickedBeds.add(this);
    }

    public void remove() {
        ClickedBeds.remove(this);
    }

    public Location getLocation() {
        if (getFirstBlock() == null) {
            return null;
        }

        return getFirstBlock().getLocation();
    }

    public Block getFirstBlock() {
        if (getBedBlocks() == null || getBedBlocks().isEmpty()) {
            return null;
        }
        return getBedBlocks().get(0);
    }

    public Block getLastBlock() {
        if (getBedBlocks() == null || getBedBlocks().isEmpty()) {
            return null;
        }
        return getBedBlocks().get(getBedBlocks().size() - 1);
    }

    public Optional<Sender> getSender() {
        return SenderUtils.getAsSender(playerUuid);
    }

    public void broadcastKill() {
        SenderUtils.broadcast("&c" + getSender().map(Sender::getName).orElse("Unknown") + " &ehas killed the &fEnder Dragon&8!");
    }

    public void setAchievement() {
        getSender().flatMap(Sender::getPlayer).ifPresent(player -> {
            AdvancementUtils.grantAdvancement(player, "minecraft", "end/kill_dragon");
        });
    }

    public boolean contains(Location location) {
        if (location == null || getBedBlocks() == null || getBedBlocks().isEmpty()) {
            return false;
        }

        return getBedBlocks().stream().anyMatch(b -> b.getLocation().distance(location) <= 0.4);
    }

    public boolean contains(Block block) {
        if (block == null || getBedBlocks() == null || getBedBlocks().isEmpty()) {
            return false;
        }

        return contains(block.getLocation()) || getBedBlocks().stream().anyMatch(b -> b.equals(block));
    }
}
