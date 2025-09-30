package dev.neuralnexus.namedentitydeathmessages.bukkit;

import dev.neuralnexus.namedentitydeathmessages.bukkit.listeners.BukkitEntityListener;
import dev.neuralnexus.namedentitydeathmessages.common.NEDMPlugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import static dev.neuralnexus.namedentitydeathmessages.common.Utils.getBukkitServerType;

/**
 * The NamedEntityDeathMessages Bukkit plugin.
 */
public class BukkitNEDMPlugin extends JavaPlugin implements NEDMPlugin {
    @Override
    public Object pluginLogger() {
        return getLogger();
    }

    @Override
    public String pluginConfigPath() {
        return "plugins";
    }

    @Override
    public String getServerType() {
        return getBukkitServerType();
    }

    @Override
    public void registerHooks() {}

    @Override
    public void registerEventListeners() {
        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new BukkitEntityListener(), this);
    }

    @Override
    public void registerCommands() {}

    @Override
    public void onEnable() {
        pluginStart();
    }

    @Override
    public void onDisable() {
        pluginStop();
    }
}
