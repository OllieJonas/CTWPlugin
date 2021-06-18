package me.ollie.capturethewool.commands.subcommands;

import me.ollie.capturethewool.core.command.AllCommands;
import me.ollie.capturethewool.core.command.ISubCommand;
import me.ollie.capturethewool.core.command.annotations.OperatorCommand;
import me.ollie.capturethewool.core.command.annotations.SubCommand;
import me.ollie.capturethewool.core.command.internal.context.SubCommandContext;
import org.bukkit.entity.Player;

@SubCommand(name = "allcommandstest", root = AllCommands.class)
@OperatorCommand
public class AllCommandsTest implements ISubCommand {

    @Override
    public void execute(Player player, SubCommandContext context) {

    }
}
