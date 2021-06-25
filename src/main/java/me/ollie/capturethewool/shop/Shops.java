package me.ollie.capturethewool.shop;

import me.ollie.capturethewool.core.shop.Shop;
import me.ollie.capturethewool.shop.exotic.ExoticCommodityRegistry;
import me.ollie.capturethewool.shop.regular.CommodityRegistry;
import org.bukkit.entity.Player;

import java.util.Locale;
import java.util.Optional;

public class Shops {

    public static class Factory {
        public static Optional<Shop> getShop(Player player, String name) {
            return switch (name.toLowerCase(Locale.ROOT)) {
                case "iron" -> Optional.of(new Iron(player));
                case "gold" -> Optional.of(new Gold(player));
                case "diamond" -> Optional.of(new Diamond(player));
                case "exotic" -> Optional.of(new Exotic(player));
                case "exchange" -> Optional.of(new Exchange(player));
                default -> Optional.empty();
            };
        }
    }

    public static class Iron extends Shop {

        public Iron(Player player) {
            super(player, "Iron Shop (Press ESC to Exit)", CommodityRegistry.getIronCommodities());
        }
    }

    public static class Gold extends Shop {

        public Gold(Player player) {
            super(player, "Gold Shop (Press ESC to Exit)", CommodityRegistry.getGoldCommodities());
        }
    }

    public static class Diamond extends Shop {

        public Diamond(Player player) {
            super(player, "Diamond Shop (Press ESC to Exit)", CommodityRegistry.getDiamondCommodities());
        }
    }

    public static class Exotic extends Shop {

        public Exotic(Player player) {
            super(player, "Exotic Shop (Press ESC to Exit)", ExoticCommodityRegistry.getCommodities());
        }
    }

    public static class Exchange extends Shop {

        public Exchange(Player player) {
            super(player, "Exchange Shop (Press ESC to Exit)", CommodityRegistry.getExchangeCommodities());
        }
    }
}
