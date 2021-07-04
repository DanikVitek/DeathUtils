package com.danikvitek.deathutils.comands;

import com.danikvitek.deathutils.Main;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
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
                    assert deathLoc != null;
                    String deathLocStr = "X: " + deathLoc.getBlockX() + " Y: " + deathLoc.getBlockY() + " Z: " + deathLoc.getBlockZ(),
                            deathWorldStr = "World: " + (getWorldsNames(main).get(Objects.requireNonNull(deathLoc.getWorld()).getName()) != null ?
                                    getWorldsNames(main).get(deathLoc.getWorld().getName()) :
                                    deathLoc.getWorld().getName());

                    TextComponent deathLocMessage = new TextComponent(ChatColor.GOLD + "Your last death position: " + ChatColor.YELLOW + deathLocStr + ", " + deathWorldStr);
                    if (player.hasPermission(Main.CAN_DEATH_TP)) {
                        deathLocMessage.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.GREEN + "Click to teleport.")));
                        deathLocMessage.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/deathtp "+player.getName()));
                    }
                    player.spigot().sendMessage(deathLocMessage);
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
