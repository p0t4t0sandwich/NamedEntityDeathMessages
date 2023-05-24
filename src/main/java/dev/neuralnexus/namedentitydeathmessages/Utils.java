package dev.neuralnexus.namedentitydeathmessages;

import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.server.ServerLifecycleHooks;

public class Utils {
    private static final ForgeMain mod = ForgeMain.getInstance();
    private static final MinecraftServer server = ServerLifecycleHooks.getCurrentServer();

    private boolean bank = false;

    // Get player max health
    public static double getPlayerMaxHealth(Player player) {
        return player.getAttribute(Attributes.MAX_HEALTH).getValue();
    }

    // Set player max health
    public static void setPlayerMaxHealth(Player player, double maxHealth) {
        double currentHealth = player.getHealth();
        double currentMaxHealth = getPlayerMaxHealth(player);

        // Add max health modifier
        player.getAttribute(Attributes.MAX_HEALTH).addTransientModifier(
            new AttributeModifier(
                "maxHealth",
                    maxHealth,
                AttributeModifier.Operation.ADDITION
        ));

        // Subtract current health
        player.getAttribute(Attributes.MAX_HEALTH).addTransientModifier(
                new AttributeModifier(
                        "maxHealth",
                        currentMaxHealth*-1,
                        AttributeModifier.Operation.ADDITION
                ));

        if (currentHealth > maxHealth) {
            player.setHealth((float) maxHealth);
        }
    }

    // Update player max health
    public static void updatePlayerMaxHealth(Player player) {
        double groupHealth = getGroupHealth();
        if (getPlayerMaxHealth(player) != groupHealth) setPlayerMaxHealth(player, groupHealth);
    }

    // Set all players max health
    public static void setAllPlayersMaxHealth(double maxHealth) {
        server.getPlayerList().getPlayers().forEach(player -> setPlayerMaxHealth(player, maxHealth));
    }

    // Update all players max health
    public static void updateAllPlayersMaxHealth() {
        double groupHealth = getGroupHealth();
        server.getPlayerList().getPlayers().forEach(player -> {
            if (getPlayerMaxHealth(player) != groupHealth) setPlayerMaxHealth(player, groupHealth);
        });
    }

    // Get group health from config
    public static double getGroupHealth() {
        return mod.config.getDouble("groupHealth");
    }

    // Save group health to config
    public static void setGroupHealth(double groupHealth) {
        try {
            mod.config.set("groupHealth", groupHealth);
            mod.config.save();
        } catch (Exception e) {
            mod.logger.error("Error saving config file");
            e.printStackTrace();
        }
    }

    // Send message to all players
    public static void broadcastMessage(String message) {
        server.getPlayerList().getPlayers().forEach(player -> player.displayClientMessage(Component.empty().append(message), false));
    }

    // Command handler
    public static String handleCommand(String[] args) {
        if (args.length == 0) {
            return "§cInvalid command! Use §6/hch help §cfor help.";
        }
        switch (args[0].toLowerCase()) {
            // Reset group health
//            case "resethealth":
//                double health = 20.0f;
//                setGroupHealth(health);
//                setAllPlayersMaxHealth(health);
//                return "§aGroup health has been reset to §620§a!";

            // Set group health
            case "sethealth":
                if (args.length == 2) {
                    try {
                        double groupHealth = Double.parseDouble(args[1]);
                        setGroupHealth(groupHealth);
                        setAllPlayersMaxHealth(groupHealth);
                        return "§aGroup health has been set to §6" + groupHealth + "§a!";
                    } catch (NumberFormatException e) {
                        return "§cInvalid number!";
                    }
                } else {
                    return "§cInvalid command! Use §6/hch help §cfor help.";
                }

            // Add health to group
            case "addhealth":
                if (args.length == 2) {
                    try {
                        double groupHealth = getGroupHealth() + Double.parseDouble(args[1]);
                        setGroupHealth(groupHealth);
                        setAllPlayersMaxHealth(groupHealth);
                        return "§aGroup health has been increased to §6" + groupHealth + "§a!";
                    } catch (NumberFormatException e) {
                        return "§cInvalid number!";
                    }
                } else {
                    return "§cInvalid command! Use §6/hch help §cfor help.";
                }
            // Bank health command
//            case "bank":
//                if (args.length == 2) {
//
//                }
            case "help":
                return "§aHardcore Health Challenge Commands:\n" +
                        "§6/hch sethealth <health> §a- Set group health to <health>\n" +
                        "§6/hch addhealth <health> §a- Add <health> to group health\n" +
                        "§6/hch help §a- Show this message";
            default:
                return "§cInvalid command! Use §6/hch help §cfor help.";
        }
    }
}
