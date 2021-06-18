package me.ollie.capturethewool.commands.subcommands;

import me.ollie.capturethewool.commands.CaptureTheWoolCommand;
import me.ollie.capturethewool.core.command.ICommand;
import me.ollie.capturethewool.core.command.annotations.CommandAliases;
import me.ollie.capturethewool.core.command.annotations.OpCommand;
import me.ollie.capturethewool.core.command.annotations.SubCommand;
import org.bukkit.entity.Player;

import java.util.List;

@SubCommand(name = "helloworld", root = CaptureTheWoolCommand.class)
@CommandAliases("hw")
@OpCommand
public class HelloWorldCommand implements ICommand {

    @Override
    public void execute(Player player, List<String> args) {
        player.sendMessage("Hello world!");
    }
}
