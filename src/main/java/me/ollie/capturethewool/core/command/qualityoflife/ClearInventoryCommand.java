package me.ollie.capturethewool.core.command.qualityoflife;

import me.ollie.capturethewool.core.command.meta.CommandStatus;
import me.ollie.capturethewool.core.command.meta.IRootCommand;
import me.ollie.capturethewool.core.command.meta.annotations.CommandAliases;
import me.ollie.capturethewool.core.command.meta.annotations.CommandInfo;
import me.ollie.capturethewool.core.command.meta.annotations.OperatorCommand;
import me.ollie.capturethewool.core.command.meta.annotations.RootCommand;
import me.ollie.capturethewool.core.command.meta.internal.context.RootCommandContext;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

@RootCommand("clearinventory")
@CommandAliases("ci")
@OperatorCommand
@CommandInfo(
        shortDescription = "Clears your inventory"
)
public class ClearInventoryCommand implements IRootCommand {

    @Override
    public CommandStatus execute(Player player, RootCommandContext context) {
        player.sendMessage(ChatColor.AQUA + "Cleared your inventory!");
        player.getInventory().clear();
        return CommandStatus.COMPLETED;
    }
}
