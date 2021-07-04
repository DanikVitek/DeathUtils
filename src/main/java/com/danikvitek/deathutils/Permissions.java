package com.danikvitek.deathutils;

import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

public enum Permissions {
    CAN_SUICIDE(new Permission("deathutils.command.suicide")),
    CAN_KNOW_DEATH_LOCATION(new Permission("deathutils.knowdeath")),
    CAN_REMEMBER_DEATH_LOCATION(new Permission("deathutils.command.remember")),
    CAN_USE_DAMAGE(new Permission("deathutils.command.damage")),
    CAN_DEATH_TP(new Permission("deathutils.command.deathtp")),
    CAN_DEATH_TP_TO_OTHERS(new Permission("deathutils.command.deathtp.to_others"));

    private final Permission perm;

    private Permissions(Permission perm){
        this.perm = perm;
    }

    public Permission getPerm() { return perm; }

    public static void init_permissions(){
        CAN_SUICIDE.perm.setDefault(PermissionDefault.TRUE);
        CAN_SUICIDE.perm.setDescription("If the player can use /suicide command");

        CAN_KNOW_DEATH_LOCATION.perm.setDefault(PermissionDefault.OP);
        CAN_KNOW_DEATH_LOCATION.perm.setDescription("If the player can get death location info on death");

        CAN_REMEMBER_DEATH_LOCATION.perm.setDefault(PermissionDefault.OP);
        CAN_REMEMBER_DEATH_LOCATION.perm.setDescription("If the player can use /remember command");

        CAN_USE_DAMAGE.perm.setDefault(PermissionDefault.OP);
        CAN_USE_DAMAGE.perm.setDescription("If the player can use /damage command");

        CAN_DEATH_TP.perm.setDefault(PermissionDefault.OP);
        CAN_DEATH_TP.perm.setDescription("If the player can use /deathtp command");

        CAN_DEATH_TP_TO_OTHERS.perm.setDefault(PermissionDefault.OP);
        CAN_DEATH_TP.perm.setDescription("If the player can use /deathtp command to teleport to other players' death locations");
    }
}
