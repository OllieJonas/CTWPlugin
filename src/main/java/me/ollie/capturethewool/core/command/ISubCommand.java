package me.ollie.capturethewool.core.command;

import me.ollie.capturethewool.core.command.internal.ICommand;
import me.ollie.capturethewool.core.command.internal.context.SubCommandContext;
import org.bukkit.entity.Player;

public interface ISubCommand extends ICommand {
    void execute(Player player, SubCommandContext context);
}
