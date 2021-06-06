package me.ollie.capturethewool.commands.subcommands;

import me.ollie.capturethewool.core.command.SubCommand;
import me.ollie.capturethewool.core.explosion.Explosion;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;

public class ExplosionCommand extends SubCommand {


    public ExplosionCommand() {
        super("explosion", true);
    }

    @Override
    public void execute(Player player, String aliasUsed, List<String> args) {

        if (args.size() == 1) {
            Explosion.getJanitor().cleanup();
            return;
        }

        Location loc = player.rayTraceBlocks(40, FluidCollisionMode.ALWAYS).getHitPosition().toLocation(player.getWorld());

        Explosion explosion = new Explosion(loc, 5);
        explosion.explode();
    }
}
