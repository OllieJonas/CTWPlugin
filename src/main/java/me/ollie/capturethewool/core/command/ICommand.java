package me.ollie.capturethewool.core.command;

import me.ollie.capturethewool.core.command.annotations.CommandInfo;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public interface ICommand {
    void execute(Player player, List<String> args);
}
