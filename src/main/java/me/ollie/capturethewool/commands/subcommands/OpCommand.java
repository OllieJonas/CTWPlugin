package me.ollie.capturethewool.commands.subcommands;

import me.ollie.capturethewool.commands.CaptureTheWoolCommand;
import me.ollie.capturethewool.core.command.ICommand;
import me.ollie.capturethewool.core.command.annotations.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

@SubCommand(name = "opme", root = CaptureTheWoolCommand.class)
public class OpCommand implements ICommand {

    @Override
    public void execute(Player player, List<String> args) {
        player.sendMessage(ChatColor.RED + "You are no longer op!");
    }
}
