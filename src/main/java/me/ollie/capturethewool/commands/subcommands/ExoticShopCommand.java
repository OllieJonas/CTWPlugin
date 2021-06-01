package me.ollie.capturethewool.commands.subcommands;

import me.ollie.capturethewool.core.command.SubCommand;
import me.ollie.capturethewool.core.gui.GUIManager;
import me.ollie.capturethewool.shop.exotic.ExoticShop;
import org.bukkit.entity.Player;

import java.util.List;

public class ExoticShopCommand extends SubCommand {

    public ExoticShopCommand() {
        super("exoticshop", true);
    }

    @Override
    public void execute(Player player, String aliasUsed, List<String> args) {
        ExoticShop shop = new ExoticShop(player);
        shop.init();

        GUIManager.getInstance().openGuiFor(player, shop);
    }
}
