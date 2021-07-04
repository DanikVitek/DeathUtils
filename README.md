# DeathUtils 1.3

## Features

* Player is now able to get information ablout the last death location of his/her character (needs permission).
* Player can get information of the last death location if needed by using `/remember` (needs permission).
* Player can teleport to the last death location by clicking on the death location info message or with the command `/deathtp` (needs permission).
* Player can teleport to the other player's last death location by using `/deathtp [player]` (needs permission).
* Player can kill his/her character.

---

`/suicide` – kills the character;

`/remember` – tells the last death location (it is saved in the file DeathUtils/death_coordinates.yml);

`/damage` – deals damage to the Damageble entities (Usage: /damage <target> <amount> [has_source]);
  
`/deathtp` – teleports the player to the last death location of the specified player, if specified. Else – to the own one.

---

## Configurations

In config.yml now you can list all of your worlds and their names in death and `/remember` messages will be represented respectively with what you have specified. 
If the world name is not specified, there will be world folder name in the death message instead.

---

## Permissions

`deathutils.knowdeath` – if the player can see their death location info on death (Default: op);

`deathutils.command.remember` – if the player can use /remember command to see their last death location info (Default: op);

`deathutils.command.suicide` – if the player can use /suicide command (Default: true);

`deathutils.command.damage` – if the player can use /damage command (Default: op);
  
`deathutils.command.deathtp` – if the player can use /deathtp command (Default: op);
  
`deathutils.command.deathtp.to_others` – if the player can use /deathtp command to teleport to the other player's last death location (Default: op).

---

There might be futher updates for the plugin, e.g. graves.
