package me.ollie.capturethewool.commands.subcommands;

import me.ollie.capturethewool.CaptureTheWool;
import me.ollie.capturethewool.core.command.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;

import java.util.List;

public class EntityRiseGroundCommand extends SubCommand {

    public EntityRiseGroundCommand() {
        super("entityriseground", true, "erg");
    }

    @Override
    public void execute(Player player, String aliasUsed, List<String> args) {
        riseGround(player.getLocation().add(player.getLocation().getDirection().setY(0).multiply(3.0)));
    }

    private void riseGround(Location location) {
        Location start = location.clone().subtract(0, 2, 0);
        Skeleton skeleton = start.getWorld().spawn(start, Skeleton.class);
        skeleton.setInvulnerable(true);
        double distance = 2.0;
        double speed = 0.1;

        int taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(CaptureTheWool.getInstance(), () -> {
            Location newLoc = start.add(0, speed, 0);

            skeleton.teleport(newLoc);
        }, 0L, 1L);

        Bukkit.getScheduler().scheduleSyncDelayedTask(CaptureTheWool.getInstance(), () -> {
            Bukkit.getScheduler().cancelTask(taskId);
            skeleton.setInvulnerable(false);
        }, Math.round(distance / speed));


    }
}
