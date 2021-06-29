package me.ollie.capturethewool.core.command.game;

import me.ollie.capturethewool.core.command.meta.CommandStatus;
import me.ollie.capturethewool.core.command.meta.IRootCommand;
import me.ollie.capturethewool.core.command.meta.annotations.CommandInfo;
import me.ollie.capturethewool.core.command.meta.annotations.RootCommand;
import me.ollie.capturethewool.core.command.meta.internal.context.RootCommandContext;
import org.bukkit.entity.Player;

@RootCommand("game")
@CommandInfo(
        shortDescription = "Control aspects of the game"
)
public class GameCommand implements IRootCommand {

    @Override
    public CommandStatus execute(Player player, RootCommandContext context) {
        return CommandStatus.SEND_HELP;
    }
}
