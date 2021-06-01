package me.ollie.capturethewool.commands.subcommands;

import me.ollie.capturethewool.core.command.SubCommand;
import me.ollie.capturethewool.core.util.HealthDisplay;
import me.ollie.capturethewool.enemy.PowerfulZombie;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;

import java.util.List;

public class ZombieCommand extends SubCommand {

    public ZombieCommand() {
        super("zombie", true);
    }

    @Override
    public void execute(Player player, String aliasUsed, List<String> args) {
        PowerfulZombie zombie = new PowerfulZombie();
        zombie.spawn(player.getLocation());
    }
}
