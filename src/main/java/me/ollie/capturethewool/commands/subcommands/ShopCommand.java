package me.ollie.capturethewool.commands.subcommands;

import me.ollie.capturethewool.commands.CaptureTheWoolCommand;
import me.ollie.capturethewool.core.command.meta.ISubCommand;
import me.ollie.capturethewool.core.command.meta.annotations.CommandInfo;
import me.ollie.capturethewool.core.command.meta.annotations.OperatorCommand;
import me.ollie.capturethewool.core.command.meta.annotations.SubCommand;
import me.ollie.capturethewool.core.command.meta.internal.context.SubCommandContext;
import me.ollie.capturethewool.core.gui.GUIManager;
import me.ollie.capturethewool.shop.Shops;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

@SubCommand(name = "shop", root = CaptureTheWoolCommand.class)
@OperatorCommand
@CommandInfo(
        usage = "<iron/gold/diamond/exotic/exchange>",
        shortDescription = "Open the Capture the Wool shop anywhere!"
)
public class ShopCommand implements ISubCommand {

    @Override
    public void execute(Player player, SubCommandContext context) {
        List<String> args = context.args();
        if (args.size() != 1) {
            badUsage(player);
            return;
        }

        Shops.Factory.getShop(player, args.get(0)).ifPresentOrElse(shop -> {
            shop.init();
            GUIManager.getInstance().openGuiFor(player, shop);
        }, () -> player.sendMessage(ChatColor.RED + "Shop doesn't exist!"));
    }
}
