package me.ollie.capturethewool.commands.subcommands;

import me.ollie.capturethewool.commands.CaptureTheWoolCommand;
import me.ollie.capturethewool.core.command.common.HelpCommand;
import me.ollie.capturethewool.core.command.common.UsageCommand;
import me.ollie.capturethewool.core.command.meta.ISubCommand;
import me.ollie.capturethewool.core.command.meta.annotations.CommandAliases;
import me.ollie.capturethewool.core.command.meta.annotations.CommandInfo;
import me.ollie.capturethewool.core.command.meta.annotations.OperatorCommand;
import me.ollie.capturethewool.core.command.meta.annotations.SubCommand;
import me.ollie.capturethewool.core.command.meta.internal.context.SubCommandContext;
import org.bukkit.entity.Player;

@SubCommand(name = "helloworld", root = CaptureTheWoolCommand.class)
@CommandAliases({"hw", "hellow"})
@OperatorCommand
@CommandInfo(
        shortDescription = "Testing hello world command!",
        longDescription = "This is a test for the reflection method of using commands - this is just internal for developers."
)
@HelpCommand.Exclude
@UsageCommand.Exclude
public class HelloWorldCommand implements ISubCommand {

    @Override
    public void execute(Player player, SubCommandContext context) {
        player.sendMessage("Hello world!");
    }
}
