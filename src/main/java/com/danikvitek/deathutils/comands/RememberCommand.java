package com.danikvitek.deathutils.comands;

import com.danikvitek.deathutils.Main;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class RememberCommand implements CommandExecutor {
    Main main;

    public RememberCommand(Main main){
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission(Main.CAN_REMEMBER_DEATH_LOCATION)) {
                if (main.getModifyDeathCoordinatesFile().contains(player.getName())) {
                    Location deathLoc = main.getModifyDeathCoordinatesFile().getLocation(player.getName() + ".location");
                    String deathLocStr = "X: " + deathLoc.getBlockX() + " Y: " + deathLoc.getBlockY() + " Z: " + deathLoc.getBlockZ(),
                            deathWorldStr = "World: " + (getWorldsNames(main).get(deathLoc.getWorld().getName()) != null ?
                                    getWorldsNames(main).get(deathLoc.getWorld().getName()) :
                                    deathLoc.getWorld().getName());
                    player.sendMessage(
                            ChatColor.GOLD + "Your last death position: " + ChatColor.YELLOW + deathLocStr + ", " + deathWorldStr);
                } else {
                    player.sendMessage(ChatColor.GOLD + "No death notes");
                }
            }
            else
                player.sendMessage(ChatColor.DARK_RED + "You have no permission to do that");
        } else {
            System.out.println("Can't use this command if not a player.");
        }

        return true;
    }

    public static Map<String, String> getWorldsNames(Main main) {
        Set<String> worldsNamesKeys =
                Objects.requireNonNull(main.getConfig().getConfigurationSection("worlds_names")).getKeys(false);

        Map<String, String> worldsNames = new HashMap<>();
        for (String key: worldsNamesKeys)
            worldsNames.put(key,
                    Objects.requireNonNull(main.getConfig().getConfigurationSection("worlds_names")).getString(key));

        return worldsNames;
    }
}
