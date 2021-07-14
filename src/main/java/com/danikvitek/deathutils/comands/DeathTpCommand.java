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
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player){
            Player player = (Player) sender;
            if (player.hasPermission(Permissions.CAN_DEATH_TP.getPerm())){
                if (args.length > 0)
                    if (player.hasPermission(Permissions.CAN_DEATH_TP_TO_OTHERS.getPerm())){
                        String targetPlayer = args[0];
                        if (Main.getModifyDeathCoordinatesFile().contains(targetPlayer))
                            player.teleport(Objects.requireNonNull(Main.getModifyDeathCoordinatesFile().getLocation(targetPlayer + ".location")));
                        else player.sendMessage(Main.getModifyLocalizationFile().getString("no_death_notes_for",
                                ChatColor.GOLD + "No death notes for") + " " + targetPlayer);
                    } else
                        player.sendMessage(Main.getModifyLocalizationFile().getString("no_permission",
                                ChatColor.DARK_RED + "You have no permission to do that"));
                else {
                    if (Main.getModifyDeathCoordinatesFile().contains(player.getName()))
                        player.teleport(Objects.requireNonNull(Main.getModifyDeathCoordinatesFile().getLocation(player.getName() + ".location")));
                    else player.sendMessage(Main.getModifyLocalizationFile().getString("no_death_notes_for_you",
                            ChatColor.GOLD + "No death notes for you"));
                }
            } else
                player.sendMessage(Main.getModifyLocalizationFile().getString("no_permission",
                        ChatColor.DARK_RED + "You have no permission to do that"));
        } else {
            System.out.println(Main.getModifyLocalizationFile().getString("console_cant",
                    "Can't use this command if not a player."));
        }

        return true;
    }
}
