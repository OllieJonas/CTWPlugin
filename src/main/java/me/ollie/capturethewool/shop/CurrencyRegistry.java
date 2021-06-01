package me.ollie.capturethewool.shop;

import me.ollie.capturethewool.core.shop.Currency;
import me.ollie.capturethewool.core.util.ItemStackBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class CurrencyRegistry {

    private static final String EXOTIC_CURRENCY_NAME = "Exotic Token";
    private static final String EXOTIC_CURRENCY_FORMATTED_NAME = ChatColor.GOLD + "" + ChatColor.BOLD + EXOTIC_CURRENCY_NAME;

    public static final Currency EXOTIC_CURRENCY = new Currency(
            EXOTIC_CURRENCY_NAME,
            EXOTIC_CURRENCY_FORMATTED_NAME,
            new ItemStackBuilder(Material.NETHER_STAR)
                    .withName(EXOTIC_CURRENCY_FORMATTED_NAME)
                    .withLore(ChatColor.GRAY + "Exchange this token for one exotic item at the " +
                            ChatColor.GOLD + "Exotic Shop" + ChatColor.GRAY + "!")
                    .build());

    private static final String IRON_CURRENCY_NAME = "Iron Bucks";
    private static final String IRON_CURRENCY_FORMATTED_NAME = ChatColor.WHITE + "" + ChatColor.BOLD + IRON_CURRENCY_NAME;
    public static final Currency IRON_BUCKS = new Currency(
            IRON_CURRENCY_NAME,
            IRON_CURRENCY_FORMATTED_NAME,
            new ItemStackBuilder(Material.QUARTZ)
                    .withName(IRON_CURRENCY_FORMATTED_NAME)
                    .withLore(ChatColor.GRAY + "Buy items with this at the shop!")
                    .build()
    );
}
