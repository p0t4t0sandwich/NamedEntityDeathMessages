package dev.neuralnexus.namedentitydeathmessages.forge;

import com.mojang.logging.LogUtils;

import dev.neuralnexus.namedentitydeathmessages.common.NEDM;
import dev.neuralnexus.namedentitydeathmessages.common.NEDMPlugin;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.event.server.ServerStoppedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.ServerLifecycleHooks;

/**
 * The NamedEntityDeathMessages Forge plugin.
 */
@Mod("namedentitydeathmessages")
public class ForgeNEDMPlugin implements NEDMPlugin {
    public ForgeNEDMPlugin() {
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.<LivingDeathEvent>addListener(event -> {
            try {
                Entity victim = event.getEntity();
                Entity attacker = event.getSource().getEntity();
                if (attacker != null && victim.getCustomName() != null && attacker.getCustomName() != null) {
                    Component victimName = victim.getCustomName();
                    Component attackerName = attacker.getCustomName();

                    String message = victimName.getString() + " was killed by " + attackerName.getString();
                    ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayers()
                            .forEach(player ->
                                    player.displayClientMessage(Component.nullToEmpty(message), false));
                    NEDM.useLogger(message);
                }
            } catch (NullPointerException e) {
                System.out.println(e);
            }
        });

        this.pluginStart();
    }

    @Override
    public Object pluginLogger() {
        return LogUtils.getLogger();
    }

    @Override
    public String pluginConfigPath() {
        return "config";
    }

    @Override
    public String getServerType() {
        return "Forge";
    }

    @Override
    public void registerHooks() {}

    /**
     * Called when the server is starting.
     * @param event The event.
     */
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Register LuckPerms hook
//        if (ModList.get().isLoaded("luckperms")) {
//            useLogger("LuckPerms detected, enabling LuckPerms hook.");
//            Template.addHook(new LuckPermsHook());
//        }
    }

    @Override
    public void registerEventListeners() {}

    @Override
    public void registerCommands() {}

    /**
     * Called when the server is stopping.
     * @param event The event.
     */
    @SubscribeEvent
    public void onServerStopped(ServerStoppedEvent event) {
        pluginStop();
    }
}
