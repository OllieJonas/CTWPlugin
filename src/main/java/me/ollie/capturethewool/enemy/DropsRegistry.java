package me.ollie.capturethewool.enemy;

import me.ollie.capturethewool.core.hologram.DroppedItemHologram;
import me.ollie.capturethewool.core.pve.EnemyDrops;
import me.ollie.capturethewool.core.util.ItemStackUtil;
import me.ollie.capturethewool.items.ItemRarity;
import me.ollie.capturethewool.items.PowerfulItemRegistry;
import me.ollie.capturethewool.shop.CurrencyRegistry;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class DropsRegistry {

    private static final DroppedItemHologram.OnDrop EXOTIC_DROP_NOTIFICATION = (audience, $) -> audience.forEach(p -> p.sendMessage(ChatColor.AQUA + "An exotic item has been dropped..."));

    private static final DroppedItemHologram.OnPickup EXOTIC_PICKUP_NOTIFICATION = (p, i) -> Bukkit.getOnlinePlayers().forEach(p1 -> p1.sendMessage(ChatColor.AQUA + p.getDisplayName() + " has found " + ItemStackUtil.getName(i) + "!"));

    public static final EnemyDrops NONE = EnemyDrops.builder().build();

    public static final EnemyDrops BOSS_DROPS = EnemyDrops.builder()
            .drop(1.0F, new EnemyDrops.Unique(
                    CurrencyRegistry.EXOTIC_CURRENCY::itemRepresentation,
                    (audience, $) -> {},
                    (audience, $) -> audience.forEach(p -> Bukkit.getOnlinePlayers().forEach(p1 -> p1.sendMessage(ChatColor.AQUA + p.getName() + " has found an Exotic Token!")))))
            .repeatLast(1)
            .build();

    public static EnemyDrops POWERFUL_ITEM_DROPS = EnemyDrops.builder()
            .drop(0.02F, EnemyDrops.Special.of(() -> PowerfulItemRegistry.randomItemFrom(ItemRarity.COMMON, ItemRarity.UNCOMMON, ItemRarity.RARE).getItemStack()))
            .drop(0.01F, EnemyDrops.Special.of(() -> PowerfulItemRegistry.randomItemFrom(ItemRarity.LEGENDARY).getItemStack()))
            .drop(1.0F, new EnemyDrops.Unique(
                    () -> PowerfulItemRegistry.randomItemFrom(ItemRarity.EXOTIC).getItemStack(), EXOTIC_PICKUP_NOTIFICATION, EXOTIC_DROP_NOTIFICATION))
            .build();

    public static EnemyDrops IRON_DROPS = EnemyDrops.builder()
            .drops(POWERFUL_ITEM_DROPS)
            .drop(1.0F, new EnemyDrops.Normal(CurrencyRegistry.IRON_BUCKS.itemRepresentation(), 2))
            .build();
}
