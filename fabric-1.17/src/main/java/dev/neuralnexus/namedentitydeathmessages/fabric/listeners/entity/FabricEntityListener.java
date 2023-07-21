package dev.neuralnexus.namedentitydeathmessages.fabric.listeners.entity;

import dev.neuralnexus.namedentitydeathmessages.common.Template;
import dev.neuralnexus.namedentitydeathmessages.fabric.FabricNEDMPlugin;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.text.Text;

public class FabricEntityListener {
    /**
     * Called when an entity dies.
     * @param entity The entity that died.
     */
    public static void onEntityDeath(LivingEntity entity, DamageSource source) {
        try {
            Entity victim = entity;
            Entity attacker = source.getAttacker();
            if (attacker != null && victim.getCustomName() != null && attacker.getCustomName() != null) {
                Text victimName = victim.getCustomName();
                Text attackerName = attacker.getCustomName();

                String message = victimName.getString() + " was killed by " + attackerName.getString();
                FabricNEDMPlugin.server.getPlayerManager().getPlayerList().forEach(player -> player.sendMessage(Text.of(message), false));
                Template.useLogger(message);
            }
        } catch (NullPointerException e) {
            System.out.println(e);
        }
    }
}
