package me.ollie.capturethewool.shop.exotic;

import me.ollie.capturethewool.core.shop.Shop;
import org.bukkit.entity.Player;

public class ExoticShop extends Shop {

    public ExoticShop(Player player) {
        super(player, "Exotic Shop (Press ESC to Exit)", ExoticCommodityRegistry.getCommodities());
    }
}
