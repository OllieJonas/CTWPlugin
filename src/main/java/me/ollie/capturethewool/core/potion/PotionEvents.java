package me.ollie.capturethewool.core.potion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PotionEvents implements Listener {

    @EventHandler
    public void onSplash(PotionSplashEvent event) {
        ItemStack item = event.getPotion().getItem();
        if (!item.hasItemFlag(ItemFlag.HIDE_POTION_EFFECTS))
            return;

        try {
            PotionContext context = PotionContext.fromItem(item);
            context.getPotionEffectType().forEach(potion -> event.getAffectedEntities().forEach(e -> e.addPotionEffect(new PotionEffect(potion, context.getDuration(), context.getAmplitude()))));

        } catch (IllegalStateException ignored) {
            event.getAffectedEntities().forEach(e -> e.sendMessage("rip potion not found"));
        }
    }

    @EventHandler
    public void onConsume(PlayerItemConsumeEvent event) {
        if (event.getItem().getType() != Material.POTION)
            return;

        ItemStack item = event.getItem();

        if (!item.hasItemFlag(ItemFlag.HIDE_POTION_EFFECTS))
            return;

        try {
            PotionContext context = PotionContext.fromItem(item);
            context.getPotionEffectType().forEach(potion -> event.getPlayer().addPotionEffect(new PotionEffect(potion, context.getDuration(), context.getAmplitude())));
        } catch (IllegalStateException e) {
            e.printStackTrace();
            event.getPlayer().sendMessage(ChatColor.RED + "Potion doesn't exist rip");
        }
    }

    @Getter
    @AllArgsConstructor
    private static class PotionContext {
        private final List<PotionEffectType> potionEffectType;
        private final int duration;
        private final int amplitude;

        public static PotionContext fromItem(ItemStack item) {
            return fromLore(Objects.requireNonNull(item.getLore()));
        }

        public static PotionContext fromLore(List<String> lore) {

            if (lore == null || lore.size() != 3)
                throw new IllegalStateException("Lore not correct");

            List<String> potionEffectTypeName = Arrays.stream(lore.get(0).split(" ")[2].split(",")).map(ChatColor::stripColor).collect(Collectors.toList());
            List<PotionEffectType> potionEffectType = potionEffectTypeName.stream().map(PotionEffectType::getByName).collect(Collectors.toList());

            int amplitude = Integer.parseInt(ChatColor.stripColor(lore.get(1).split(" ")[1])) - 1;
            int duration = Integer.parseInt(ChatColor.stripColor(lore.get(2).split(" ")[1])) * 20;

            return new PotionContext(potionEffectType, duration, amplitude);
        }

        @Override
        public String toString() {
            return "PotionContext{" +
                    "potionEffectType=" + potionEffectType.stream().map(PotionEffectType::getName).collect(Collectors.joining(", ")) +
                    ", duration=" + duration +
                    ", amplitude=" + amplitude +
                    '}';
        }
    }
}
