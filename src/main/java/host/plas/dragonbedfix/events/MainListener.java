package host.plas.dragonbedfix.events;

import host.plas.dragonbedfix.DragonBedFix;
import host.plas.dragonbedfix.data.ClickedBed;
import host.plas.dragonbedfix.data.ClickedBeds;
import host.plas.dragonbedfix.utils.BedUtils;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.damage.DamageSource;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class MainListener extends AbstractConglomerate {
    @EventHandler
    public void onDragonDeath(EntityDeathEvent event) {
        Entity entity = event.getEntity();
        if (! (entity instanceof EnderDragon)) return;
        EnderDragon dragon = (EnderDragon) entity;

        EntityDamageEvent damageEvent = dragon.getLastDamageCause();
        if (damageEvent == null) return;
        EntityDamageEvent.DamageCause cause = damageEvent.getCause();
        if (cause != EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) return;

        DamageSource source = damageEvent.getDamageSource();
        Location location = source.getSourceLocation();
        if (location == null) {
            DragonBedFix.getInstance().logWarning("Could not get source location of damage event for dragon death!");
            return;
        }
        if (location.getWorld() == null) {
            DragonBedFix.getInstance().logWarning("Source location of damage event for dragon death has no world! Fixing!");
            location.setWorld(dragon.getWorld());
        }

        if (! ClickedBeds.contains(location.getBlock())) return;

        ClickedBed bed = ClickedBeds.get(location.getBlock()).orElse(null);
        if (bed == null) return;

        if (DragonBedFix.getMainConfig().isBroadcastKill()) bed.broadcastKill();
        bed.setAchievement();
    }

    @EventHandler
    public void onClickBed(PlayerInteractEvent event) {
        if (event.getHand() == EquipmentSlot.OFF_HAND) return;
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK && event.getAction() != Action.RIGHT_CLICK_AIR) return;

        Player player = event.getPlayer();
        String uuid = player.getUniqueId().toString();

        Block block = event.getClickedBlock();
        if (block == null) {
            block = player.getTargetBlockExact(15, FluidCollisionMode.NEVER);
        }
        if (! BedUtils.isBed(block)) return;

        ClickedBed bed = new ClickedBed(BedUtils.getBedBlocks(block), uuid);
    }
}
