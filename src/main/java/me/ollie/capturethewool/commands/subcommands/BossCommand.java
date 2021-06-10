package me.ollie.capturethewool.commands.subcommands;

import me.ollie.capturethewool.core.command.SubCommand;
import me.ollie.capturethewool.boss.pirateboss.TheCaptain;
import org.bukkit.entity.Player;

import java.util.List;

public class BossCommand extends SubCommand {

    public BossCommand() {
        super("boss", true);
    }

    @Override
    public void execute(Player player, String aliasUsed, List<String> args) {
        TheCaptain captain = new TheCaptain();
        captain.spawn(TheCaptain.SPAWN);
    }
}
