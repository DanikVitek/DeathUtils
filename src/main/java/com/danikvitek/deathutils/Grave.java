//package com.danikvitek.deathutils;
//
//import org.bukkit.Bukkit;
//import org.bukkit.ChatColor;
//import org.bukkit.command.Command;
//import org.bukkit.command.CommandExecutor;
//import org.bukkit.command.CommandSender;
//import org.bukkit.entity.Player;
//import org.bukkit.event.EventHandler;
//import org.bukkit.event.Listener;
//import org.bukkit.event.entity.PlayerDeathEvent;
//import org.bukkit.event.inventory.InventoryCloseEvent;
//import org.bukkit.inventory.Inventory;
//import org.bukkit.inventory.ItemStack;
//
//import java.io.IOException;
//import java.util.Arrays;
//import java.util.Objects;
//
//public class Grave implements Listener, CommandExecutor {
//    @EventHandler
//    public void onDeath(PlayerDeathEvent event) throws IOException {
//        if (!event.getKeepInventory()) {
//            Player player = event.getEntity();
//            Inventory graveInventory = Bukkit.createInventory(null, 45, "Grave of " + player.getName());
//
//            for (int i = 0; i < player.getInventory().getArmorContents().length/*= 4*/; i++)
//                graveInventory.setItem(i, player.getInventory().getArmorContents()[i]);
//            graveInventory.setItem(4, player.getInventory().getItemInOffHand());
//            for (int i = 0; i < player.getInventory().getStorageContents().length - 9; i++)
//                graveInventory.setItem(i + 9, player.getInventory().getStorageContents()[i + 9]);
//            for (int i = 0; i < 9; i++)
//                graveInventory.setItem(i + 36, player.getInventory().getStorageContents()[i]);
//
//            Main.getModifyGraveFile().set(player.getName(), graveInventory.getContents());
//            Main.getModifyGraveFile().save(Main.getGravesFile());
//
//            event.getDrops().removeAll(event.getDrops());
//        }
//    }
//
//    @Override
//    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
//        if (sender instanceof Player){
//            Player player = (Player) sender;
//            if (player.hasPermission(Permissions.CAN_USE_OPENGRAVE.getPerm())) {
//                if (args.length < 1){
//                    if (Main.getModifyGraveFile().contains(player.getName())) {
//                        Inventory graveInventory = Bukkit.createInventory(null, 45, "Grave of " + player.getName());
//                        graveInventory.setContents((ItemStack[]) Objects.requireNonNull(Main.getModifyGraveFile().get(player.getName())));
//                        player.openInventory(graveInventory);
//                    }
//                    else player.sendMessage(ChatColor.RED + "Grave not found!");
//                } else {
//                    if (player.hasPermission(Permissions.CAN_USE_OPENGRAVE_OTHERS.getPerm())) {
//                        if (Main.getModifyGraveFile().contains(args[0])) {
//                            Inventory graveInventory = Bukkit.createInventory(null, 45, "Grave of " + player.getName());
//                            graveInventory.setContents((ItemStack[]) Objects.requireNonNull(Main.getModifyGraveFile().get(args[0])));
//                            player.openInventory(graveInventory);
//                        }
//                        else player.sendMessage(ChatColor.RED + "Grave not found!");
//                    }
//                }
//            }
//        } else {
//            System.out.println("Can't use this command if not a player.");
//        }
//
//        return true;
//    }
//
//    @EventHandler
//    public void onGraveChanged(InventoryCloseEvent event) throws IOException {
//        if (event.getView().getTitle().contains("Grave of ")) {
//            String owner = event.getView().getTitle().replace("Grave of ", "");
//            if (Main.getModifyGraveFile().contains(owner)
//                    && !Arrays.equals((ItemStack[]) Main.getModifyGraveFile().get(owner), event.getInventory().getContents())) {
//                Main.getModifyGraveFile().set(owner, event.getInventory().getContents());
//                Main.getModifyGraveFile().save(Main.getGravesFile());
//            }
//        }
//    }
//}
