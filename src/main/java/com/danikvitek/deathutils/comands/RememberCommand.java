package com.danikvitek.deathutils.comands;

import com.danikvitek.deathutils.Main;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RememberCommand implements CommandExecutor {
    Main main;

    public RememberCommand(Main main){
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (main.getModifyDeathCoordinatesFile().contains(player.getName())) {
                Location deathLoc = main.getModifyDeathCoordinatesFile().getLocation(player.getName() + ".location");
                String deathLocStr = "X: " + deathLoc.getBlockX() + " Y: " + deathLoc.getBlockY() + " Z: " + deathLoc.getBlockZ(),
                        deathWorldStr = "World: " + deathLoc.getWorld().getName();
                player.sendMessage(
                        ChatColor.GOLD + "Your last death position: " + ChatColor.YELLOW + deathLocStr + ", " + deathWorldStr);
            } else {
                player.sendMessage(ChatColor.GOLD + "No death notes");
            }
        } else {
            System.out.println("Can't use this command if not a player.");
        }

        return false;
    }
}
