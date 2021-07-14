package com.danikvitek.deathutils;

import com.danikvitek.deathutils.comands.DamageCommand;
import com.danikvitek.deathutils.comands.DeathTpCommand;
import com.danikvitek.deathutils.comands.RememberCommand;
import com.danikvitek.deathutils.comands.SuicideCommand;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public final class Main extends JavaPlugin implements Listener {
    public String pluginName = "DeathUtils";

    private static File deathCoordinatesFile;
    private static YamlConfiguration modifyDeathCoordinatesFile;
    private static File localizationFile;
    private static YamlConfiguration modifyLocalizationFile;
//    private static File gravesFile;
//    private static YamlConfiguration modifyGraveFile;

    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("DeathUtils plugin ENABLED");

        this.getConfig().options().copyDefaults();
        saveDefaultConfig();

        Permissions.init_permissions();

        getCommand("suicide").setExecutor(new SuicideCommand());
        getCommand("remember").setExecutor(new RememberCommand(this));
        getCommand("damage").setExecutor(new DamageCommand());
        getCommand("deathtp").setExecutor(new DeathTpCommand());
//        getCommand("opengrave").setExecutor(new Grave());

        Bukkit.getPluginManager().registerEvents(this, this);
//        Bukkit.getPluginManager().registerEvents(new Grave(), this);

        try {
            initFiles();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static File getDeathCoordinatesFile() { return deathCoordinatesFile; }
    public static YamlConfiguration getModifyDeathCoordinatesFile() { return modifyDeathCoordinatesFile; }
    public static File getLocalizationFile() {
        return localizationFile;
    }
    public static YamlConfiguration getModifyLocalizationFile() {
        return modifyLocalizationFile;
    }
    //    public static File getGravesFile() { return gravesFile; }
//    public static YamlConfiguration getModifyGraveFile() { return modifyGraveFile; }

    public void initFiles() throws IOException {
        deathCoordinatesFile = new File(Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin(pluginName)).getDataFolder(),
                "death_coordinates.yml");
        if (!deathCoordinatesFile.exists())
            deathCoordinatesFile.createNewFile();
        modifyDeathCoordinatesFile = YamlConfiguration.loadConfiguration(deathCoordinatesFile);

        localizationFile = new File(Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin(pluginName)).getDataFolder(),
                "localization.yml");
        if (!localizationFile.exists())
            localizationFile.createNewFile();
        modifyLocalizationFile = YamlConfiguration.loadConfiguration(localizationFile);
        modifyLocalizationFile.set("world", "World");
        modifyLocalizationFile.set("your_death_position", ChatColor.GOLD + "Your death position: " + ChatColor.YELLOW);
        modifyLocalizationFile.set("your_last_death_position", ChatColor.GOLD + "Your death position: " + ChatColor.YELLOW);
        modifyLocalizationFile.set("click_to_teleport", ChatColor.GREEN + "Click to teleport");
        modifyLocalizationFile.set("no_death_notes", ChatColor.GOLD + "No death notes");
        modifyLocalizationFile.set("no_death_notes_for", ChatColor.GOLD + "No death notes for ");
        modifyLocalizationFile.set("no_death_notes_for_you", ChatColor.GOLD + "No death notes for you");
        modifyLocalizationFile.set("entity_cant_be_damages", ChatColor.RED + "Entity can't be damaged");
        modifyLocalizationFile.set("no_permission", ChatColor.DARK_RED + "You have no permission to do that");
        modifyLocalizationFile.set("console_cant", "Can't use this command if not a player.");

        modifyLocalizationFile.save(localizationFile);

//        gravesFile = new File(Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin(pluginName)).getDataFolder(),
//                "graves.yml");
//        if (!gravesFile.exists())
//            gravesFile.createNewFile();
//        modifyGraveFile = YamlConfiguration.loadConfiguration(gravesFile);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) throws IOException {
        Player player = event.getEntity();

        Location deathLoc = player.getLocation();
        World deathWorld = deathLoc.getWorld();
        String deathLocStr = "X: " + deathLoc.getBlockX() + " Y: " + deathLoc.getBlockY() + " Z: " + deathLoc.getBlockZ();
        assert deathWorld != null;
        String deathWorldStr = modifyLocalizationFile.getString("world", "World") + ": " +
                (RememberCommand.getWorldsNames(this).get(deathWorld.getName()) != null ?
                RememberCommand.getWorldsNames(this).get(deathWorld.getName()) : deathWorld.getName());

        modifyDeathCoordinatesFile.set(player.getName() + ".location", deathLoc);
        modifyDeathCoordinatesFile.save(deathCoordinatesFile);

        if (player.hasPermission(Permissions.CAN_KNOW_DEATH_LOCATION.getPerm())) {
            TextComponent deathLocMessage = new TextComponent(modifyLocalizationFile.getString("your_death_position",
                    ChatColor.GOLD + "Your death position: " + ChatColor.YELLOW) + deathLocStr + ", " + deathWorldStr);
            if (player.hasPermission(Permissions.CAN_DEATH_TP.getPerm())){
                deathLocMessage.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                        new Text(modifyLocalizationFile.getString("click_to_teleport",ChatColor.GREEN + "Click to teleport"))));
                deathLocMessage.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/deathtp "+player.getName()));
            }
            player.spigot().sendMessage(deathLocMessage);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("DeathUtils plugin DISABLED");
    }
}
