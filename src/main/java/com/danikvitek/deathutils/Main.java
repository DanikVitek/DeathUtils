package com.danikvitek.deathutils;

import com.danikvitek.deathutils.comands.RememberCommand;
import com.danikvitek.deathutils.comands.SuicideCommand;
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

public final class Main extends JavaPlugin implements Listener {
    private File deathCoordinatesFile;
    private YamlConfiguration modifyDeathCoordinatesFile;


    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("DeathUtils plugin ENABLED");

        this.getConfig().options().copyDefaults();
        saveDefaultConfig();

        getCommand("suicide").setExecutor(new SuicideCommand());
        getCommand("remember").setExecutor(new RememberCommand(this));

        Bukkit.getPluginManager().registerEvents(this, this);

        try {
            initFiles();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File getDeathCoordinatesFile() { return deathCoordinatesFile; }
    public YamlConfiguration getModifyDeathCoordinatesFile() { return modifyDeathCoordinatesFile; }

    public void initFiles() throws IOException {
        deathCoordinatesFile = new File(Bukkit.getServer().getPluginManager().getPlugin("Test").getDataFolder(),
                "death_coordinates.yml");
        if (!deathCoordinatesFile.exists())
            deathCoordinatesFile.createNewFile();

        modifyDeathCoordinatesFile = YamlConfiguration.loadConfiguration(deathCoordinatesFile);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) throws IOException {
        Player player = event.getEntity();

        Location deathLoc = player.getLocation();
        World deathWorld = deathLoc.getWorld();
        String deathLocStr = "X: " + deathLoc.getBlockX() + " Y: " + deathLoc.getBlockY() + " Z: " + deathLoc.getBlockZ();
        String deathWorldStr = "World: " + deathWorld.getName();

        player.sendMessage(
                ChatColor.GOLD + "Your death position: " + ChatColor.YELLOW + deathLocStr + ", " + deathWorldStr);

//        Map<String, Integer> deathLocMap = new HashMap<String, Integer>();
//        deathLocMap.put("x", deathLoc.getBlockX());
//        deathLocMap.put("y", deathLoc.getBlockY());
//        deathLocMap.put("z", deathLoc.getBlockZ());

        modifyDeathCoordinatesFile.set(player.getName() + ".location", deathLoc);
//        modifyDeathCoordinatesFile.set(player.getName() + ".world", deathWorld.getName());

        modifyDeathCoordinatesFile.save(deathCoordinatesFile);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("DeathUtils plugin DISABLED");
    }
}
