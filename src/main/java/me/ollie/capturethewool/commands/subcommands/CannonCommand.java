package me.ollie.capturethewool.commands.subcommands;

import me.ollie.capturethewool.boss.abilities.powerful.CannonFire;
import me.ollie.capturethewool.core.command.SubCommand;
import org.bukkit.entity.Player;

import java.util.List;

public class CannonCommand extends SubCommand {

    private final CannonFire cannonFire = new CannonFire(CannonFire.CANNON_LOCATIONS);

    public CannonCommand() {
        super("cannon", true);
    }

    @Override
    public void execute(Player player, String aliasUsed, List<String> args) {
        cannonFire.power(player);
    }
}
