package me.ollie.capturethewool.commands;

import me.ollie.capturethewool.commands.subcommands.*;
import me.ollie.capturethewool.core.command.ICommand;
import me.ollie.capturethewool.core.command.annotations.CommandAliases;
import me.ollie.capturethewool.core.command.annotations.CommandInfo;
import me.ollie.capturethewool.core.command.annotations.RootCommand;
import org.bukkit.entity.Player;

import java.util.List;

@RootCommand("ctw")
@CommandAliases("capturethewool")
@CommandInfo(
        usage = "/ctw",
        shortDescription = "Main command for Capture the Wool"
)
public class CaptureTheWoolCommand implements ICommand {

    public CaptureTheWoolCommand() {

    }

    @Override
    public void execute(Player player, List<String> args) {

    }
}
