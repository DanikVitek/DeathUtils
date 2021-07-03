package com.danikvitek.deathutils;

import com.danikvitek.deathutils.comands.DamageCommand;
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
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class Main extends JavaPlugin implements Listener {
    public String pluginName = "DeathUtils";

    private File deathCoordinatesFile;
    private YamlConfiguration modifyDeathCoordinatesFile;

    public static Permission CAN_SUICIDE = new Permission("deathutils.command.suicide");
    public static Permission CAN_KNOW_DEATH_LOCATION = new Permission("deathutils.knowdeath");
    public static Permission CAN_REMEMBER_DEATH_LOCATION = new Permission("deathutils.command.remember");
    public static Permission CAN_USE_DAMAGE = new Permission("deathutils.command.damage");

    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("DeathUtils plugin ENABLED");

        this.getConfig().options().copyDefaults();
        saveDefaultConfig();

        init_permissions();

        getCommand("suicide").setExecutor(new SuicideCommand());
        getCommand("remember").setExecutor(new RememberCommand(this));
        getCommand("damage").setExecutor(new DamageCommand());

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
        deathCoordinatesFile = new File(Bukkit.getServer().getPluginManager().getPlugin(pluginName).getDataFolder(),
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
        String deathWorldStr = "World: " +
                (RememberCommand.getWorldsNames(this).get(deathWorld.getName()) != null ?
                RememberCommand.getWorldsNames(this).get(deathWorld.getName()) : deathWorld.getName());

        modifyDeathCoordinatesFile.set(player.getName() + ".location", deathLoc);
        modifyDeathCoordinatesFile.save(deathCoordinatesFile);

        if (player.hasPermission(CAN_KNOW_DEATH_LOCATION))
            player.sendMessage(
                    ChatColor.GOLD + "Your death position: " + ChatColor.YELLOW + deathLocStr + ", " + deathWorldStr);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("DeathUtils plugin DISABLED");
    }

    public void init_permissions(){
        CAN_SUICIDE.setDefault(PermissionDefault.TRUE);
        CAN_SUICIDE.setDescription("If the player can use /suicide command");

        CAN_KNOW_DEATH_LOCATION.setDefault(PermissionDefault.OP);
        CAN_KNOW_DEATH_LOCATION.setDescription("If the player can get death location info on death");

        CAN_REMEMBER_DEATH_LOCATION.setDefault(PermissionDefault.OP);
        CAN_REMEMBER_DEATH_LOCATION.setDescription("If the player can use /remember command");

        CAN_USE_DAMAGE.setDefault(PermissionDefault.OP);
        CAN_USE_DAMAGE.setDescription("If the player can use /damage command");
    }
}
