package dev.neuralnexus.namedentitydeathmessages.forge;

import dev.neuralnexus.namedentitydeathmessages.common.Template;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.server.ServerLifecycleHooks;

public class ForgeEventHandler {
    @SubscribeEvent
    public static void onDeathEvent(LivingDeathEvent event) {
        try {
            Entity victim = event.getEntity();
            Entity attacker = event.getSource().getEntity();
            if (attacker != null && victim.getCustomName() != null && attacker.getCustomName() != null) {
                Component victimName = victim.getCustomName();
                Component attackerName = attacker.getCustomName();

                String message = victimName.getString() + " was killed by " + attackerName.getString();
                ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayers().forEach(player -> player.displayClientMessage(Component.empty().append(message), false));
                Template.useLogger(message);
            }
        } catch (NullPointerException e) {
            System.out.println(e);
        }
    }
}
