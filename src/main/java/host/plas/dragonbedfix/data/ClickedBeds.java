package host.plas.dragonbedfix.data;

import host.plas.dragonbedfix.DragonBedFix;
import host.plas.dragonbedfix.utils.LocationUtils;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.Optional;
import java.util.concurrent.ConcurrentSkipListSet;

public class ClickedBeds {
    @Getter @Setter
    private static ConcurrentSkipListSet<ClickedBed> clickedBeds = new ConcurrentSkipListSet<>();

    public static void add(ClickedBed clicked) {
        if (clicked.getLocation().getWorld().getEnvironment() != World.Environment.THE_END) return;
        if (contains(clicked.getIdentifier())) remove(clicked);

        clickedBeds.add(clicked);

        DragonBedFix.getInstance().logInfo("Loaded new clicked bed: " + clicked.getIdentifier() + " at " + LocationUtils.locationToString(clicked.getLocation()));
    }

    public static void remove(String identifier) {
        clickedBeds.removeIf(clickedBed -> clickedBed.getIdentifier().equals(identifier));
    }

    public static void remove(Location location) {
        clickedBeds.removeIf(clickedBed -> clickedBed.contains(location));
    }

    public static void remove(Block block) {
        clickedBeds.removeIf(clickedBed -> clickedBed.contains(block));
    }

    public static void remove(ClickedBed clicked) {
        remove(clicked.getIdentifier());
    }

    public static Optional<ClickedBed> get(String identifier) {
        return clickedBeds.stream()
                .filter(clickedBed -> clickedBed.getIdentifier().equals(identifier))
                .findFirst();
    }

    public static Optional<ClickedBed> get(Location location) {
        return clickedBeds.stream()
                .filter(clickedBed -> clickedBed.contains(location))
                .findFirst();
    }

    public static Optional<ClickedBed> get(Block block) {
        return clickedBeds.stream()
                .filter(clickedBed -> clickedBed.contains(block))
                .findFirst();
    }

    public static boolean contains(String identifier) {
        return get(identifier).isPresent();
    }

    public static boolean contains(ClickedBed clicked) {
        return contains(clicked.getIdentifier());
    }

    public static boolean contains(Location location) {
        return getClickedBeds().stream().anyMatch(clickedBed -> clickedBed.contains(location));
    }

    public static boolean contains(Block block) {
        return getClickedBeds().stream().anyMatch(clickedBed -> clickedBed.contains(block));
    }
}
