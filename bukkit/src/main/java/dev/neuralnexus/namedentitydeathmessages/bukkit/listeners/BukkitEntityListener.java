package dev.neuralnexus.namedentitydeathmessages.bukkit.listeners;

import dev.neuralnexus.namedentitydeathmessages.common.Template;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class BukkitEntityListener implements Listener {
    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        Entity victim = event.getEntity();
        Entity attacker = event.getEntity().getKiller();

        if (attacker != null && victim.getCustomName() != null && attacker.getCustomName() != null) {
            String victimName = victim.getCustomName();
            String attackerName = attacker.getCustomName();

            String message = victimName + " was killed by " + attackerName;
            event.getEntity().getWorld().getPlayers().forEach(player -> player.sendMessage(message));
            Template.useLogger(message);
        }
    }
}
