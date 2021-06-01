package me.ollie.capturethewool.items.bows;

import me.ollie.capturethewool.core.GamesCore;
import me.ollie.capturethewool.core.util.HomingArrow;
import me.ollie.capturethewool.items.ItemRarity;
import me.ollie.capturethewool.items.meta.AbilityInformation;
import me.ollie.capturethewool.items.types.PowerfulBow;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityShootBowEvent;

public class HomingBow extends PowerfulBow {

    public HomingBow() {
        super("Homing Bow", "This is a fun bow :)", ItemRarity.RARE, AbilityInformation.of(
                "Homing Arrows",
                "Arrows are homing!",
                2.0F
        ));
    }

    @Override
    public void onShoot(EntityShootBowEvent event) {
        Player player = (Player) event.getEntity();
        HomingArrow.decorate(GamesCore.getInstance().getPlugin(), (Arrow) event.getProjectile()).shoot(player);
    }
}
