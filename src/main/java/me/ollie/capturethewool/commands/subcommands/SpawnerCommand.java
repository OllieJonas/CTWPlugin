package me.ollie.capturethewool.commands.subcommands;

import me.ollie.capturethewool.CaptureTheWool;
import me.ollie.capturethewool.core.command.SubCommand;
import me.ollie.capturethewool.core.pve.Spawner;
import me.ollie.capturethewool.enemy.PowerfulZombie;
import org.bukkit.entity.Player;

import java.util.List;

public class SpawnerCommand extends SubCommand {

    public SpawnerCommand() {
        super("spawner", true);
    }

    @Override
    public void execute(Player player, String aliasUsed, List<String> args) {
        Spawner<PowerfulZombie> spawner = new Spawner<>(CaptureTheWool.getInstance(), player.getLocation(), PowerfulZombie::new, 4, 6);
        spawner.place();
    }
}
