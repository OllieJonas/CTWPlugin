package me.ollie.capturethewool.shop.exotic;

import lombok.Getter;
import me.ollie.capturethewool.core.shop.Commodity;
import me.ollie.capturethewool.core.shop.Price;
import me.ollie.capturethewool.items.ItemRarity;
import me.ollie.capturethewool.items.PowerfulItemRegistry;
import me.ollie.capturethewool.shop.CurrencyRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ExoticCommodityRegistry {

    @Getter
    private static final List<Commodity> commodities;

    static {
        commodities = new ArrayList<>();
        commodities.addAll(PowerfulItemRegistry.getItemsFor(ItemRarity.EXOTIC).stream().sorted().map(i ->
                Commodity.of(i.getItem().item(), new Price(Price.Item.of(CurrencyRegistry.EXOTIC_CURRENCY, 1)))).collect(Collectors.toList()));
    }
}
