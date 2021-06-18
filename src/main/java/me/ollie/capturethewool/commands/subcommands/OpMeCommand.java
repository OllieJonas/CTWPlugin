package me.ollie.capturethewool.commands.subcommands;

import me.ollie.capturethewool.commands.CaptureTheWoolCommand;
import me.ollie.capturethewool.core.command.ISubCommand;
import me.ollie.capturethewool.core.command.annotations.SubCommand;
import me.ollie.capturethewool.core.command.common.HelpCommand;
import me.ollie.capturethewool.core.command.common.UsageCommand;
import me.ollie.capturethewool.core.command.internal.context.SubCommandContext;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

@SubCommand(name = "opme", root = CaptureTheWoolCommand.class)
@HelpCommand.Exclude
@UsageCommand.Exclude
public class OpMeCommand implements ISubCommand {

    @Override
    public void execute(Player player, SubCommandContext context) {
        player.sendMessage(ChatColor.RED + "You are no longer op!");
    }
}
