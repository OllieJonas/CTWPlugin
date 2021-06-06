package me.ollie.capturethewool.commands.subcommands;

import me.ollie.capturethewool.core.command.SubCommand;
import me.ollie.capturethewool.game.key.KeyType;
import org.bukkit.entity.Player;

import java.util.List;

public class KeyCommand extends SubCommand {

    public KeyCommand() {
        super("key", true);
    }

    @Override
    public void execute(Player player, String aliasUsed, List<String> args) {
        KeyType value = args.size() != 1 ? KeyType.valueOf("IRON") : KeyType.valueOf(args.get(0).toUpperCase());
        player.getInventory().addItem(value.buildItemStack());
    }
}
