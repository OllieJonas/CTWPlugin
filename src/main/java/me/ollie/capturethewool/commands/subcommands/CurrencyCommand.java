package me.ollie.capturethewool.commands.subcommands;

import me.ollie.capturethewool.commands.CaptureTheWoolCommand;
import me.ollie.capturethewool.core.command.meta.ISubCommand;
import me.ollie.capturethewool.core.command.meta.annotations.CommandInfo;
import me.ollie.capturethewool.core.command.meta.annotations.OperatorCommand;
import me.ollie.capturethewool.core.command.meta.annotations.SubCommand;
import me.ollie.capturethewool.core.command.meta.internal.context.SubCommandContext;
import me.ollie.capturethewool.shop.CurrencyRegistry;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

@SubCommand(name = "currency", root = CaptureTheWoolCommand.class)
@OperatorCommand
@CommandInfo(
        usage = "<iron/gold/diamond/exotic>",
        shortDescription = "Give yourself a certain amount of currency!"
)
public class CurrencyCommand implements ISubCommand {

    @Override
    public void execute(Player player, SubCommandContext context) {
        List<String> args = context.args();
        if (args.size() != 1) {
            badUsage(player);
            return;
        }

        CurrencyRegistry.Factory.getCurrency(args.get(0)).ifPresentOrElse(
                c -> player.getInventory().addItem(c.itemRepresentation().asQuantity(4)),
                () -> player.sendMessage(ChatColor.RED + "Currency doesn't exist!"));
    }
}
