package me.ollie.capturethewool.core.command.lobby;

import me.ollie.capturethewool.core.command.meta.CommandStatus;
import me.ollie.capturethewool.core.command.meta.IRootCommand;
import me.ollie.capturethewool.core.command.meta.annotations.RootCommand;
import me.ollie.capturethewool.core.command.meta.internal.context.RootCommandContext;
import org.bukkit.entity.Player;

@RootCommand("lobby")
public class LobbyCommand implements IRootCommand {

    @Override
    public CommandStatus execute(Player player, RootCommandContext context) {
        return CommandStatus.SEND_HELP;
    }
}
