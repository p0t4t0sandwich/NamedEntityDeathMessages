package dev.neuralnexus.namedentitydeathmessages.fabric;

import dev.neuralnexus.namedentitydeathmessages.common.NEDMPlugin;
import dev.neuralnexus.namedentitydeathmessages.fabric.events.entity.FabricEntityEvents;
import dev.neuralnexus.namedentitydeathmessages.fabric.listeners.entity.FabricEntityListener;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;
import org.slf4j.LoggerFactory;

/**
 * The Template Fabric plugin.
 */
public class FabricNEDMPlugin implements DedicatedServerModInitializer, NEDMPlugin {
    public static MinecraftServer server;

    @Override
    public Object pluginLogger() {
        return LoggerFactory.getLogger("taterapi");
    }

    @Override
    public String pluginConfigPath() {
        return "config";
    }

    @Override
    public String getServerType() {
        return "Fabric";
    }

    @Override
    public void registerHooks() {
        ServerLifecycleEvents.SERVER_STARTING.register(server -> FabricNEDMPlugin.server = server);
    }

    @Override
    public void registerEventListeners() {
        FabricEntityEvents.DEATH.register(FabricEntityListener::onEntityDeath);
    }

    @Override
    public void registerCommands() {}

    @Override
    public void onInitializeServer() {
        pluginStart();
        ServerLifecycleEvents.SERVER_STOPPED.register(server -> pluginStop());
    }
}
