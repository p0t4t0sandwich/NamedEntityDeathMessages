package dev.neuralnexus.namedentitydeathmessages.fabric.events.entity;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;

/**
 * Contains additional entity events.
 */
public final class FabricEntityEvents {
    /**
     * Called when an entity dies.
     */
    public static final Event<EntityDeath> DEATH = EventFactory.createArrayBacked(EntityDeath.class, (listeners) -> (entity, source) -> {
        for (EntityDeath listener : listeners) {
            listener.onEntityDeath(entity, source);
        }
    });

    @FunctionalInterface
    public interface EntityDeath {
        void onEntityDeath(LivingEntity entity, DamageSource source);
    }
}
