# DeathUtils 1.2

At the moment this plugin lets the player to get information of where did he/she died and to kill their character for any reason (survival and adventure mode only).

---

`/suicide` – kills the character;

`/remember` – tells the last death location (it is saved in the file DeathUtils/death_coordinates.yml);

`/damage` – deals damage to the Damageble entities (Usage: /damage <target> <amount> [has_source]).

---

## Configurations

In config.yml now you can list all of your worlds and their names in death and `/remember` messages will be represented respectively with what you have specified. 
If the world name is not specified, there will be world folder name in the death message instead.

---

## Permissions

`deathutils.knowdeath` – if the player can see their death location info on death (Default: op);

`deathutils.command.remember` – if the player can use /remember command to see their last death location info (Default: op);

`deathutils.command.suicide` – if the player can use /suicide command (Default: true);

`deathutils.command.damage` – if the player can use /damage (Default: op).

---

There might be futher updates for the plugin, e.g. graves.
