package me.ollie.capturethewool.core.command.meta;

import me.ollie.capturethewool.core.command.meta.internal.ICommand;
import me.ollie.capturethewool.core.command.meta.internal.context.SubCommandContext;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public interface ISubCommand extends ICommand {
    void execute(Player player, SubCommandContext context);

    default void badUsage(Player player) {
        player.sendMessage(ChatColor.RED + "Invalid usage!");
    }
}
