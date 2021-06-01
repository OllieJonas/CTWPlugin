package me.ollie.capturethewool.commands.subcommands;

import me.ollie.capturethewool.core.command.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

public class GCFreeCommand extends SubCommand {

    public GCFreeCommand() {
        super("gcfree", true);
    }

    @Override
    public void execute(Player player, String aliasUsed, List<String> args) {
        player.sendMessage(ChatColor.RED + "WARNING - THIS COMMAND IS EXTREMELY UNSAFE!! PLEASE DON'T USE UNLESS YOU KNOW WHAT YOU'RE DOING");
        player.sendMessage(ChatColor.RED + "THIS WILL ALMOST CERTAINLY CAUSE LAG!");
        player.sendMessage(ChatColor.AQUA + "Calling for garbage collection...");
        System.gc();
    }
}
