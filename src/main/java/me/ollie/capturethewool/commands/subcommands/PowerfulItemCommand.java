package me.ollie.capturethewool.commands.subcommands;

import me.ollie.capturethewool.core.command.SubCommand;
import me.ollie.capturethewool.items.PowerfulItemRegistry;
import me.ollie.capturethewool.items.types.PowerfulItem;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

public class PowerfulItemCommand extends SubCommand {

    public PowerfulItemCommand() {
        super("powerfulitem", true, "item");
    }

    @Override
    public void execute(Player player, String aliasUsed, List<String> args) {

        if (args.size() != 1) return;

        PowerfulItem item = PowerfulItemRegistry.get(args.get(0));

        if (item == null) {
            player.sendMessage(ChatColor.RED + "Item doesn't exist!");
            return;
        }

        player.getInventory().addItem(item.getItemStack());
    }
}
