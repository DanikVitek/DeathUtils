package com.danikvitek.deathutils.comands;

import com.danikvitek.deathutils.Main;
import com.danikvitek.deathutils.Permissions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DamageCommand implements CommandExecutor {

    public static final String usage = "Usage: /damage <target> <amount> [has_source]";

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;

            if (player.hasPermission(Permissions.CAN_USE_DAMAGE.getPerm())){
                try {
                    if (args.length < 2) throw new NullPointerException();
                    String target = args[0];
                    int amount = Integer.parseInt(args[1]);
                    boolean hasSource = args.length == 3 && Boolean.parseBoolean(args[2]);
                    if (amount < 0)
                        throw new NumberFormatException();


                    if (Bukkit.getOnlinePlayers().stream().map((Function<Player, String>) HumanEntity::getName)
                            .collect(Collectors.toList()).contains(target)){
                        Player targetPlayer = Bukkit.getPlayer(target);
                        assert targetPlayer != null;
                        targetPlayer.damage(amount, hasSource ? player : null);
                    } else if (Objects.requireNonNull(Bukkit.getEntity(UUID.fromString(target))).isValid()
                    && Bukkit.getEntity(UUID.fromString(target)) instanceof Damageable)
                        ((Damageable) Objects.requireNonNull(Bukkit.getEntity(UUID.fromString(target))))
                                .damage(amount, hasSource ? player : null);
                    else player.sendMessage(Main.getModifyLocalizationFile().getString("entity_cant_be_damaged",
                                ChatColor.RED + "Entity can't be damaged"));

                } catch (NullPointerException | ArrayIndexOutOfBoundsException e) {
                    player.sendMessage(ChatColor.RED + usage);
                } catch (NumberFormatException e){
                    player.sendMessage(ChatColor.RED + "Amount must be integer and greater than 0");
                }
            }
            else
                player.sendMessage(Main.getModifyLocalizationFile().getString("no_permission",
                        ChatColor.DARK_RED + "You have no permission to do that"));
        } else {
            try {
                if (args.length < 2) throw new NullPointerException();
                String target = args[0];
                int amount = Integer.parseInt(args[1]);
                if (amount < 0)
                    throw new NumberFormatException();


                if (Bukkit.getOnlinePlayers().stream().map((Function<Player, String>) HumanEntity::getName)
                        .collect(Collectors.toList()).contains(target)){
                    Player targetPlayer = Bukkit.getPlayer(target);
                    assert targetPlayer != null;
                    targetPlayer.damage(amount);
                } else if (Objects.requireNonNull(Bukkit.getEntity(UUID.fromString(target))).isValid()
                        && Bukkit.getEntity(UUID.fromString(target)) instanceof Damageable)
                    ((Damageable) Objects.requireNonNull(Bukkit.getEntity(UUID.fromString(target))))
                            .damage(amount);
                else System.out.println(Main.getModifyLocalizationFile().getString("entity_cant_be_damaged",
                             "Entity can't be damaged"));

            } catch (NullPointerException | ArrayIndexOutOfBoundsException e) {
                System.out.println(usage);
            } catch (NumberFormatException e){
                System.out.println("Amount must be integer and greater than 0");
            }
        }

        return true;
    }
}
