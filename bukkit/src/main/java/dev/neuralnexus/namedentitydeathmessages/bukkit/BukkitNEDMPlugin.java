package dev.neuralnexus.namedentitydeathmessages.bukkit;

import dev.neuralnexus.namedentitydeathmessages.bukkit.listeners.BukkitEntityListener;
import dev.neuralnexus.namedentitydeathmessages.common.Template;
import dev.neuralnexus.namedentitydeathmessages.common.NEDMPlugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import static dev.neuralnexus.namedentitydeathmessages.common.Utils.getBukkitServerType;

/**
 * The NamedEntityDeathMessages Bukkit plugin.
 */
public class BukkitNEDMPlugin extends JavaPlugin implements NEDMPlugin {
    /**
     * @inheritDoc
     */
    @Override
    public Object pluginLogger() {
        return getLogger();
    }

    /**
     * @inheritDoc
     */
    @Override
    public String pluginConfigPath() {
        return "plugins";
    }

    /**
     * @inheritDoc
     */
    @Override
    public String getServerType() {
        return getBukkitServerType();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void registerHooks() {}

    /**
     * @inheritDoc
     */
    @Override
    public void registerEventListeners() {
        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new BukkitEntityListener(), this);
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
    public void onEnable() {
        pluginStart();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void onDisable() {
        pluginStop();
    }
}
