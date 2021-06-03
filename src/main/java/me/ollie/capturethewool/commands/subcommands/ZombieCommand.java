package me.ollie.capturethewool.commands.subcommands;

import me.ollie.capturethewool.CaptureTheWool;
import me.ollie.capturethewool.core.bossbar.MobBossBar;
import me.ollie.capturethewool.core.command.SubCommand;
import me.ollie.capturethewool.core.util.HealthDisplay;
import me.ollie.capturethewool.enemy.PowerfulZombie;
import net.kyori.adventure.bossbar.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;

import java.util.*;

public class ZombieCommand extends SubCommand {

    public ZombieCommand() {
        super("zombie", true);
    }

    @Override
    public void execute(Player player, String aliasUsed, List<String> args) {
        PowerfulZombie zombie = new PowerfulZombie();
        Zombie z = zombie.spawn(player.getLocation());
        MobBossBar bossBar = new MobBossBar(CaptureTheWool.getInstance(), z, BossBar.Color.GREEN, new HashSet<>(List.of(player)));
    }
}
