package me.ollie.capturethewool.commands;

import me.ollie.capturethewool.core.command.IRootCommand;
import me.ollie.capturethewool.core.command.annotations.CommandAliases;
import me.ollie.capturethewool.core.command.annotations.CommandInfo;
import me.ollie.capturethewool.core.command.annotations.RootCommand;
import me.ollie.capturethewool.core.command.internal.context.RootCommandContext;
import org.bukkit.entity.Player;

@RootCommand("ctw")
@CommandAliases("capturethewool")
@CommandInfo(
        usage = "/ctw",
        shortDescription = "Main command for Capture the Wool"
)
public class CaptureTheWoolCommand implements IRootCommand {

    @Override
    public void execute(Player player, RootCommandContext context) {

    }
}
