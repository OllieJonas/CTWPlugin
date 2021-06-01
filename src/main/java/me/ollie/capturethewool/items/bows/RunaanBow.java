package me.ollie.capturethewool.items.bows;

import me.ollie.capturethewool.core.GamesCore;
import me.ollie.capturethewool.core.util.HomingArrow;
import me.ollie.capturethewool.core.util.VectorUtil;
import me.ollie.capturethewool.items.ItemRarity;
import me.ollie.capturethewool.items.meta.AbilityInformation;
import me.ollie.capturethewool.items.types.PowerfulBow;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;

public class RunaanBow extends PowerfulBow {

    public RunaanBow() {
        super("Runaan's Bow", "a fun bow for your friends and family", ItemRarity.LEGENDARY, AbilityInformation.of("Homing Arrows", "Right click to shoot 3 homing arrows!", 1.5F));
    }

    @Override
    public void onShoot(EntityShootBowEvent event) {
        Player player = (Player) event.getEntity();
        Location location = player.getEyeLocation().clone();
        Vector direction = location.getDirection().normalize();
        Vector plusFortyFiveDegrees = VectorUtil.rotateAroundYAxis(direction.clone(), 10);
        Vector minusFortyFiveDegrees =  VectorUtil.rotateAroundYAxis(direction.clone(), -10);

        Arrow arrow = (Arrow) event.getProjectile();
        arrow.remove();

        double speed = arrow.getVelocity().length();
        World world = player.getWorld();

        Arrow left = world.spawnArrow(location, plusFortyFiveDegrees, (float) speed, 0F);
        Arrow centre = world.spawnArrow(location, direction, (float) speed, 0F);
        Arrow right = world.spawnArrow(location, minusFortyFiveDegrees, (float) speed, 0F);

        HomingArrow.decorate(GamesCore.getInstance().getPlugin(), left).shoot(player, 0);
        HomingArrow.decorate(GamesCore.getInstance().getPlugin(), centre).shoot(player, 0);
        HomingArrow.decorate(GamesCore.getInstance().getPlugin(), right).shoot(player, 0);
    }
}
