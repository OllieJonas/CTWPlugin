package me.ollie.capturethewool.core.command.meta;

import me.ollie.capturethewool.core.command.meta.internal.ICommand;
import me.ollie.capturethewool.core.command.meta.internal.context.RootCommandContext;
import org.bukkit.entity.Player;

public interface IRootCommand extends ICommand {
    CommandStatus execute(Player player, RootCommandContext context);
}
