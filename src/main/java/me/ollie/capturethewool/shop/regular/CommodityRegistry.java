package me.ollie.capturethewool.shop.regular;

import lombok.Getter;
import me.ollie.capturethewool.core.shop.Commodity;
import me.ollie.capturethewool.core.shop.Price;
import me.ollie.capturethewool.core.util.ItemStackBuilder;
import me.ollie.capturethewool.items.ItemRarity;
import me.ollie.capturethewool.items.types.RarityItem;
import me.ollie.capturethewool.shop.CurrencyRegistry;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Collection;
import java.util.List;

public class CommodityRegistry {

    @Getter
    private static final Collection<Commodity> ironCommodities;

    private static final ItemStack POISON_ARROW = new ItemStackBuilder.PotionBuilder(Material.TIPPED_ARROW, new ItemStackBuilder()
            .withName("Arrow of Poison"))
            .potionEffect(new PotionEffect(PotionEffectType.POISON, 8 * 20, 0))
            .colour(Color.GREEN)
            .buildPotion();

    @Getter
    private static final Collection<Commodity> goldCommodities;

    @Getter
    private static final Collection<Commodity> diamondCommodities;

    @Getter
    private static final Collection<Commodity> exchangeCommodities;

    static {
        ironCommodities = List.of(
                new Commodity(new RarityItem(Material.TORCH, ItemRarity.UNCOMMON).item().asQuantity(4), new Price(Price.Item.of(CurrencyRegistry.IRON_BUCKS, 3))),
                new Commodity(new RarityItem(Material.BREAD, ItemRarity.UNCOMMON).item().asQuantity(8), new Price(Price.Item.of(CurrencyRegistry.IRON_BUCKS, 4))),
                new Commodity(new RarityItem(Material.GRAY_STAINED_GLASS, ItemRarity.UNCOMMON).item().asQuantity(32), new Price(Price.Item.of(CurrencyRegistry.IRON_BUCKS, 6))),
                new Commodity(new RarityItem(Material.LADDER, ItemRarity.UNCOMMON).item().asQuantity(24), new Price(Price.Item.of(CurrencyRegistry.IRON_BUCKS, 6))),
                new Commodity(new RarityItem(Material.COBWEB, ItemRarity.UNCOMMON).item().asQuantity(8), new Price(Price.Item.of(CurrencyRegistry.IRON_BUCKS, 8))),
                new Commodity(new RarityItem(Material.ARROW, ItemRarity.UNCOMMON).item().asQuantity(16), new Price(Price.Item.of(CurrencyRegistry.IRON_BUCKS, 8))),
                new Commodity(new RarityItem(POISON_ARROW, ItemRarity.UNCOMMON).item().asQuantity(8), new Price(Price.Item.of(CurrencyRegistry.IRON_BUCKS, 16)))
        );

        goldCommodities = List.of(
                new Commodity(new RarityItem(Material.OAK_WOOD, ItemRarity.RARE).item().asQuantity(64), new Price(Price.Item.of(CurrencyRegistry.GOLD_BUCKS, 2))),
                new Commodity(new RarityItem(Material.COOKED_BEEF, ItemRarity.RARE).item().asQuantity(4), new Price(Price.Item.of(CurrencyRegistry.GOLD_BUCKS, 3))),
                new Commodity(new RarityItem(Material.SCAFFOLDING, ItemRarity.RARE).item().asQuantity(24), new Price(Price.Item.of(CurrencyRegistry.GOLD_BUCKS, 12))),
                new Commodity(new RarityItem(Material.TNT, ItemRarity.RARE).item().asQuantity(8), new Price(Price.Item.of(CurrencyRegistry.GOLD_BUCKS, 20)))
        );

        diamondCommodities = List.of(
                new Commodity(new RarityItem(Material.APPLE, ItemRarity.LEGENDARY).item().asQuantity(4), new Price(Price.Item.of(CurrencyRegistry.DIAMOND_BUCKS, 1))),
                new Commodity(new RarityItem(Material.LAVA_BUCKET, ItemRarity.LEGENDARY).item().asQuantity(4), new Price(Price.Item.of(CurrencyRegistry.DIAMOND_BUCKS, 8))),
                new Commodity(new RarityItem(Material.EXPERIENCE_BOTTLE, ItemRarity.LEGENDARY).item().asQuantity(64), new Price(Price.Item.of(CurrencyRegistry.DIAMOND_BUCKS, 10)))
        );

        exchangeCommodities = List.of(
                new Commodity(CurrencyRegistry.GOLD_BUCKS.itemRepresentation().asQuantity(5), new Price(Price.Item.of(CurrencyRegistry.DIAMOND_BUCKS, 1))),
                new Commodity(CurrencyRegistry.IRON_BUCKS.itemRepresentation().asQuantity(5), new Price(Price.Item.of(CurrencyRegistry.GOLD_BUCKS, 1)))
        );
    }
}
