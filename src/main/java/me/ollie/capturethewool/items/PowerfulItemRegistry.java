package me.ollie.capturethewool.items;

import lombok.Getter;
import me.ollie.capturethewool.items.bows.ExplosionBow;
import me.ollie.capturethewool.items.bows.HomingBow;
import me.ollie.capturethewool.items.bows.NetheringBow;
import me.ollie.capturethewool.items.bows.RunaanBow;
import me.ollie.capturethewool.items.items.*;
import me.ollie.capturethewool.items.meta.PowerfulItemCooldownInjector;
import me.ollie.capturethewool.items.swords.AspectOfTheEndSword;
import me.ollie.capturethewool.items.swords.AssassinsBlade;
import me.ollie.capturethewool.items.swords.RogueSword;
import me.ollie.capturethewool.items.types.PowerfulBow;
import me.ollie.capturethewool.items.types.PowerfulItem;
import me.ollie.capturethewool.items.types.PowerfulSword;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

public class PowerfulItemRegistry {

    private static final Random RANDOM = new Random();

    private static final Map<String, PowerfulSword> swords = new HashMap<>();

    private static final Map<String, PowerfulItem> items = new HashMap<>();

    private static final Map<String, PowerfulBow> bows = new HashMap<>();

    @Getter
    private static final Map<ItemRarity, List<PowerfulItem>> rarities = new HashMap<>();

    static {

        // swords
        registerSword(new AspectOfTheEndSword());
        registerSword(new RogueSword());
        registerSword(new AssassinsBlade());

        // bows
        registerBow(new ExplosionBow());
        registerBow(new HomingBow());
        registerBow(new RunaanBow());
        registerBow(new NetheringBow());

        // items
        registerItem(new Railgun());
        registerItem(new Mjolnir());
        registerItem(new WellOfRadiance());
        registerItem(new WellOfEmpowerment());
        registerItem(new WellOfMobility());

        registerAllWithCooldowns();
    }

    private static void registerBow(PowerfulBow bow) {
        bows.put(bow.getName(), bow);
        registerRarity(bow);
    }

    private static void registerSword(PowerfulSword sword) {
        swords.put(sword.getName(), sword);
        registerRarity(sword);
    }

    private static void registerItem(PowerfulItem item) {
        items.put(item.getName(), item);
        registerRarity(item);
    }

    private static void registerRarity(PowerfulItem item) {
        rarities.computeIfAbsent(item.getRarity(), k -> new ArrayList<>());
        rarities.get(item.getRarity()).add(item);
    }

    public static PowerfulItem randomItemFrom(ItemRarity... rarities) {
        return randomItemFrom(List.of(rarities));
    }

    public static PowerfulItem randomItem() {
        return randomItemFrom(ItemRarity.values());
    }

    public static PowerfulItem randomItemFrom(List<ItemRarity> rarities) {
        List<PowerfulItem> list = rarities.stream().map(PowerfulItemRegistry.rarities::get).flatMap(Collection::stream).collect(Collectors.toList());
        return list.get(RANDOM.nextInt(list.size()));
    }

    public static PowerfulItem randomItemFrom(ItemRarity rarity) {
        List<PowerfulItem> list = rarities.get(rarity);
        return list.get(RANDOM.nextInt(list.size()));
    }

    public static List<PowerfulItem> getItemsFor(ItemRarity rarity) {
        return rarities.get(rarity);
    }

    public static PowerfulItem get(String name) {
        String transformed = name.replace('_', ' ');
        if (swords.containsKey(transformed)) return swords.get(transformed);
        else if (items.containsKey(transformed)) return items.get(transformed);
        else return bows.getOrDefault(transformed, null);
    }

    private static void registerAllWithCooldowns() {
        register(swords);
        register(items);
        register(bows);
    }

    private static void register(Map<String, ? extends PowerfulItem> items) {
        items.values().forEach(PowerfulItemCooldownInjector::inject);
    }
}
