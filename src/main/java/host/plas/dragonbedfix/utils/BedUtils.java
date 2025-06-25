package host.plas.dragonbedfix.utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Bed;

import java.util.ArrayList;
import java.util.List;

public class BedUtils {
    public static boolean isBed(Material material) {
        if (material == null) return false;

        String name = material.name().toLowerCase();
        return name.endsWith("bed") || name.endsWith("bed_block");
    }

    public static boolean isBed(Block block) {
        if (block == null) return false;

        return isBed(block.getType());
    }

    public static List<Block> getBedBlocks(Block block) {
        List<Block> r = new ArrayList<>();

        if (! isBed(block)) return r;

        r.add(block);
        Block otherPart = readOtherPartOfBed(block);
        if (! isBed(otherPart)) return r;

        r.add(otherPart);

        return r;
    }

    public static Block readOtherPartOfBed(Block block) {
        if (! isBed(block)) return null;

        Bed bedData = (Bed) block.getBlockData();
        if (bedData.getPart() == Bed.Part.HEAD) {
            return block.getRelative(bedData.getFacing().getOppositeFace());
        } else {
            return block.getRelative(bedData.getFacing());
        }
    }

    public static Block readOtherPartOfBed(Location location) {
        return readOtherPartOfBed(location.getBlock());
    }
}
