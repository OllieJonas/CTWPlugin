package me.ollie.capturethewool.shop;

import me.ollie.capturethewool.core.shop.Currency;
import me.ollie.capturethewool.core.util.ItemStackBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Locale;
import java.util.Optional;

public class CurrencyRegistry {

    private static final String EXOTIC_CURRENCY_NAME = "Exotic Token";
    private static final String EXOTIC_CURRENCY_FORMATTED_NAME = ChatColor.GOLD + "" + ChatColor.BOLD + EXOTIC_CURRENCY_NAME;

    public static class Factory {
        public static Optional<Currency> getCurrency(String name) {
            return switch (name.toLowerCase(Locale.ROOT)) {
                case "iron" -> Optional.of(IRON_BUCKS);
                case "gold" -> Optional.of(GOLD_BUCKS);
                case "diamond" -> Optional.of(DIAMOND_BUCKS);
                case "exotic" -> Optional.of(EXOTIC_CURRENCY);
                default -> Optional.empty();
            };
        }
    }
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

    private static final String GOLD_CURRENCY_NAME = "Gold Bucks";
    private static final String GOLD_CURRENCY_FORMATTED_NAME = ChatColor.WHITE + "" + ChatColor.BOLD + GOLD_CURRENCY_NAME;
    public static final Currency GOLD_BUCKS = new Currency(
            GOLD_CURRENCY_NAME,
            GOLD_CURRENCY_FORMATTED_NAME,
            new ItemStackBuilder(Material.GOLD_INGOT)
                    .withName(GOLD_CURRENCY_FORMATTED_NAME)
                    .withLore(ChatColor.GRAY + "Buy items with this at the shop!")
                    .build()
    );

    private static final String DIAMOND_CURRENCY_NAME = "Diamond Bucks";
    private static final String DIAMOND_CURRENCY_FORMATTED_NAME = ChatColor.WHITE + "" + ChatColor.BOLD + DIAMOND_CURRENCY_NAME;
    public static final Currency DIAMOND_BUCKS = new Currency(
            DIAMOND_CURRENCY_NAME,
            DIAMOND_CURRENCY_FORMATTED_NAME,
            new ItemStackBuilder(Material.DIAMOND)
                    .withName(DIAMOND_CURRENCY_FORMATTED_NAME)
                    .withLore(ChatColor.GRAY + "Buy items with this at the shop!")
                    .build()
    );
}
