package me.ollie.capturethewool.core.util;

import lombok.experimental.UtilityClass;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

@UtilityClass
public class PlayerUtil {

    public void reset(Player player) {
        player.setGameMode(GameMode.SURVIVAL);
        player.getInventory().clear();
        player.setFlying(false);
        player.setAllowFlight(false);
        player.setInvisible(false);
        player.setHealth(20D);
        player.setFireTicks(0);
        player.setExp(0);
        player.setTotalExperience(0);
        player.removePotionEffect(PotionEffectType.INVISIBILITY);
        player.setLevel(0);
    }
}
