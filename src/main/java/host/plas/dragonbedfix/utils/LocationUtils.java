package host.plas.dragonbedfix.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LocationUtils {
    public static String locationToString(org.bukkit.Location location) {
        if (location == null) {
            return "null";
        }
        return String.format("%s;%s;%s;%s;%s;%s;",
                location.getWorld().getName(),
                location.getX(),
                location.getY(),
                location.getZ(),
                location.getYaw(),
                location.getPitch()
        );
    }

    public static Location stringToLocation(String locationString) {
        if (locationString == null || locationString.equals("null")) {
            return null;
        }

        String[] parts = locationString.split(";");
        if (parts.length < 6) {
            return null; // Invalid format
        }

        String worldName = parts[0];
        double x = Double.parseDouble(parts[1]);
        double y = Double.parseDouble(parts[2]);
        double z = Double.parseDouble(parts[3]);
        float yaw = Float.parseFloat(parts[4]);
        float pitch = Float.parseFloat(parts[5]);

        return new Location(Bukkit.getWorld(worldName), x, y, z, yaw, pitch);
    }
}
