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

    /**
     * @inheritDoc
     */
    @Override
    public Object pluginLogger() {
        return LoggerFactory.getLogger("taterapi");
    }

    /**
     * @inheritDoc
     */
    @Override
    public String pluginConfigPath() {
        return "config";
    }

    /**
     * @inheritDoc
     */
    @Override
    public String getServerType() {
        return "Fabric";
    }

    /**
     * @inheritDoc
     */
    @Override
    public void registerHooks() {
        ServerLifecycleEvents.SERVER_STARTING.register(server -> FabricNEDMPlugin.server = server);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void registerEventListeners() {
        FabricEntityEvents.DEATH.register(FabricEntityListener::onEntityDeath);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void registerCommands() {}

    /**
     * @inheritDoc
     */
    @Override
    public void onInitializeServer() {
        pluginStart();
        ServerLifecycleEvents.SERVER_STOPPED.register(server -> pluginStop());
    }
}
