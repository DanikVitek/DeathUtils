package com.danikvitek.deathutils.comands;

import com.danikvitek.deathutils.Main;
import com.danikvitek.deathutils.Permissions;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class DeathTpCommand implements CommandExecutor {
    Main main;

    public DeathTpCommand(Main main){
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player){
            Player player = (Player) sender;
            if (player.hasPermission(Permissions.CAN_DEATH_TP.getPerm())){
                if (args.length > 0)
                    if (player.hasPermission(Permissions.CAN_DEATH_TP_TO_OTHERS.getPerm())){
                        String targetPlayer = args[0];
                        if (main.getModifyDeathCoordinatesFile().contains(targetPlayer))
                            player.teleport(Objects.requireNonNull(main.getModifyDeathCoordinatesFile().getLocation(targetPlayer + ".location")));
                        else player.sendMessage(ChatColor.GOLD + "No death notes for " + targetPlayer);
                    } else
                        player.sendMessage(ChatColor.DARK_RED + "You have no permission to do that");
                else {
                    if (main.getModifyDeathCoordinatesFile().contains(player.getName()))
                        player.teleport(Objects.requireNonNull(main.getModifyDeathCoordinatesFile().getLocation(player.getName() + ".location")));
                    else player.sendMessage(ChatColor.GOLD + "No death notes for you");
                }
            } else
                player.sendMessage(ChatColor.DARK_RED + "You have no permission to do that");
        } else {
            System.out.println("Can't use this command if not a player.");
        }

        return true;
    }
}
