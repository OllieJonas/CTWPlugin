package me.ollie.capturethewool.core.potion;

import lombok.*;
import me.ollie.capturethewool.core.util.RomanNumberUtil;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class Potion {

    private String name;

    private boolean isSplash;

    private int amount;

    @Singular
    private List<PotionEffectType> effects;

    private int amplitude;

    private int duration;

    private Color color;


    public ItemStack toItemStack() {
        Material material = isSplash ? Material.SPLASH_POTION : Material.POTION;
        ItemStack item = new ItemStack(material);
        item.setAmount(amount);
        ItemMeta itemMeta = item.getItemMeta();

        itemMeta.setDisplayName(name == null ? buildName() : name);
        itemMeta.setLore(buildLore());
        itemMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);

        PotionMeta meta = (PotionMeta) itemMeta;

        if (color != null) {
            meta.setColor(color);
        }

        item.setItemMeta(meta);
        return item;
    }

    private String buildName() {
        StringBuilder builder = new StringBuilder();
        builder.append(ChatColor.AQUA);

        if (effects.size() > 1) {
            builder.append("Mixed").append(" ");

            if (isSplash)
                builder.append("Splash").append(" ");

            builder.append("Potion");

        } else {
            if (isSplash)
                builder.append("Splash ");
            builder.append("Potion of ");
            builder.append(PotionEffectTypeToName.convert(effects.get(0)));
        }
        builder.append(" ").append(RomanNumberUtil.toRoman(amplitude)).append(" ");
        builder.append(ChatColor.GRAY).append("(").append(duration).append(" seconds").append(")");
        return builder.toString();
    }

    private List<String> buildLore() {
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.DARK_GRAY + "Potion Effect: " + ChatColor.AQUA + effects.stream().map(PotionEffectType::getName).collect(Collectors.joining(",")));
        lore.add(ChatColor.DARK_GRAY + "Strength: " + ChatColor.AQUA + amplitude);
        lore.add(ChatColor.DARK_GRAY + "Duration: " + ChatColor.AQUA + duration + ChatColor.GRAY + " seconds");
        return lore;
    }
}

