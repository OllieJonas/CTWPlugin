package me.ollie.capturethewool.core.command;

import me.ollie.capturethewool.core.command.internal.ICommand;
import me.ollie.capturethewool.core.command.internal.context.RootCommandContext;
import org.bukkit.entity.Player;

public interface IRootCommand extends ICommand {
    void execute(Player player, RootCommandContext context);
}
