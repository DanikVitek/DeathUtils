package com.danikvitek.deathutils.comands;

import com.danikvitek.deathutils.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SuicideCommand implements CommandExecutor {
    @Override
    @SuppressWarnings("deprecation")
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;

            if (player.hasPermission(Main.CAN_SUICIDE))
                player.damage(player.getMaxHealth());
            else
                player.sendMessage(ChatColor.DARK_RED + "You have no permission to do that");
        } else {
            System.out.println("Can't use this command if not a player.");
        }

        return true;
    }
}
