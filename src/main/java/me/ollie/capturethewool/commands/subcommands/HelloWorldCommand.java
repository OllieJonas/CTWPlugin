package me.ollie.capturethewool.commands.subcommands;

import me.ollie.capturethewool.commands.CaptureTheWoolCommand;
import me.ollie.capturethewool.core.command.ISubCommand;
import me.ollie.capturethewool.core.command.annotations.CommandAliases;
import me.ollie.capturethewool.core.command.annotations.CommandInfo;
import me.ollie.capturethewool.core.command.annotations.OperatorCommand;
import me.ollie.capturethewool.core.command.annotations.SubCommand;
import me.ollie.capturethewool.core.command.internal.context.SubCommandContext;
import org.bukkit.entity.Player;

@SubCommand(name = "helloworld", root = CaptureTheWoolCommand.class)
@CommandAliases({"hw", "hellow"})
@OperatorCommand
@CommandInfo(
        shortDescription = "Testing hello world command!",
        longDescription = "This is a test for the reflection method of using commands - this is just internal for developers."
)
public class HelloWorldCommand implements ISubCommand {

    @Override
    public void execute(Player player, SubCommandContext context) {
        player.sendMessage("Hello world!");
    }
}
