package me.ollie.capturethewool.core.util;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class LocationUtil {

    public static Location behind(Player player, int amount) {
        return player.getLocation().clone().add(player.getLocation().getDirection().normalize().multiply(-1 * amount));
    }
}
