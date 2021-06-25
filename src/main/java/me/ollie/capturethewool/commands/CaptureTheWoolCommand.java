package me.ollie.capturethewool.commands;

import me.ollie.capturethewool.core.command.meta.IRootCommand;
import me.ollie.capturethewool.core.command.meta.annotations.CommandAliases;
import me.ollie.capturethewool.core.command.meta.annotations.CommandInfo;
import me.ollie.capturethewool.core.command.meta.annotations.RootCommand;
import me.ollie.capturethewool.core.command.meta.internal.context.RootCommandContext;
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
