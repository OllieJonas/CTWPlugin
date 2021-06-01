package me.ollie.capturethewool.commands.subcommands;

import me.ollie.capturethewool.core.command.SubCommand;
import me.ollie.capturethewool.core.shop.Currency;
import me.ollie.capturethewool.shop.CurrencyRegistry;
import org.bukkit.entity.Player;

import java.util.List;

public class ExoticCiphersCommand extends SubCommand {

    public ExoticCiphersCommand() {
        super("ciphers", true);
    }

    @Override
    public void execute(Player player, String aliasUsed, List<String> args) {
        Currency currency = CurrencyRegistry.EXOTIC_CURRENCY;
        player.getInventory().addItem(currency.itemRepresentation().asQuantity(10));
    }
}
