package dev.neuralnexus.namedentitydeathmessages;

import static dev.neuralnexus.namedentitydeathmessages.Utils.*;
import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Arrays;

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
                broadcastMessage(message);
                ForgeMain.logger.info(message);
            }
        } catch (NullPointerException e) {
            System.out.println(e);
        }
    }
}
