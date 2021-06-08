package me.ollie.capturethewool.commands.subcommands;

import me.ollie.capturethewool.boss.pirateboss.CannonFireAbility;
import me.ollie.capturethewool.core.command.SubCommand;
import org.bukkit.entity.Player;

import java.util.List;

public class CannonCommand extends SubCommand {

    private final CannonFireAbility cannonFire = new CannonFireAbility(CannonFireAbility.CANNON_LOCATIONS);

    public CannonCommand() {
        super("cannon", true);
    }

    @Override
    public void execute(Player player, String aliasUsed, List<String> args) {
        cannonFire.power(player);
    }
}
