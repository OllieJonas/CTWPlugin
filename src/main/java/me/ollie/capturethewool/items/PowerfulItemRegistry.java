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

public class PowerfulItemRegistry {

    private static final Map<String, PowerfulSword> swords = new HashMap<>();

    private static final Map<String, PowerfulItem> items = new HashMap<>();

    private static final Map<String, PowerfulBow> bows = new HashMap<>();

    @Getter
    private static final Set<PowerfulItem> exotics = new HashSet<>();

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
        registerExotic(bow);
    }

    private static void registerSword(PowerfulSword sword) {
        swords.put(sword.getName(), sword);
        registerExotic(sword);
    }

    private static void registerItem(PowerfulItem item) {
        items.put(item.getName(), item);
        registerExotic(item);
    }

    private static void registerExotic(PowerfulItem item) {
        if (item.getRarity() == ItemRarity.EXOTIC)
            exotics.add(item);
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

    public static boolean doesPlayerAlreadyHaveExotic(Player player) {
        return Arrays.stream(player.getInventory().getContents())
                .anyMatch(i -> exotics.stream()
                        .map(PowerfulItem::getItemStack)
                        .anyMatch(e -> e.getItemMeta().getDisplayName().equals(i.getItemMeta().getDisplayName())));
    }
}
