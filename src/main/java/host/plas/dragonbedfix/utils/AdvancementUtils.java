package host.plas.dragonbedfix.utils;

import host.plas.dragonbedfix.DragonBedFix;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.entity.Player;

public class AdvancementUtils {
    public static void grantAdvancement(Player player, String namespace, String key) {
        // Create the NamespacedKey for the advancement
        NamespacedKey namespacedKey = new NamespacedKey(namespace, key);

        // Get the advancement from the server
        Advancement advancement = Bukkit.getAdvancement(namespacedKey);

        if (advancement == null) {
            DragonBedFix.getInstance().logWarning("Advancement " + namespace + ":" + key + " does not exist!");
            return;
        }

        // Get the player's progress for the advancement
        AdvancementProgress progress = player.getAdvancementProgress(advancement);

        // Grant all criteria for the advancement
        for (String criterion : progress.getRemainingCriteria()) {
            progress.awardCriteria(criterion);
        }
    }
}
