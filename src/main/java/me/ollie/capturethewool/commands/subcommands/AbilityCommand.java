package me.ollie.capturethewool.commands.subcommands;

import me.ollie.capturethewool.boss.abilities.SkeletalHorseWave;
import me.ollie.capturethewool.core.ability.Ability;
import me.ollie.capturethewool.core.command.SubCommand;
import org.bukkit.entity.Player;

import java.util.List;

public class AbilityCommand extends SubCommand {


    public AbilityCommand() {
        super("ability", true);
    }

    @Override
    public void execute(Player player, String aliasUsed, List<String> args) {
        Ability ability = new SkeletalHorseWave();
        ability.power(player.getLocation());
    }
}
