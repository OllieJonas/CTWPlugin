package me.ollie.capturethewool.items.meta;

import me.ollie.capturethewool.core.util.ItemStackBuilder;
import me.ollie.capturethewool.items.ItemRarity;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ItemsUtil {

    public static ItemStack buildFrom(String name, Material material, String backstory, ItemRarity rarity, String abilityTitle, String abilityDescription, float cooldownDuration, Map<Enchantment, Integer> enchantments) {
        return new ItemStackBuilder(material)
                .withName(rarity.getColour() + name)
                .withLore(loreFrom(
                        rarity.getFormattedTitle(),
                        backstory,
                        abilityTitle,
                        abilityDescription,
                        cooldownDuration))
                .makeUnbreakable()
                .withGlow()
                .withEnchantments(enchantments)
                .build();
    }

    public static List<String> loreFrom(String rarity, String backstory, String abilityTitle, String abilityDescription, float cooldownDuration) {
        List<String> lore = new ArrayList<>();
        ChatColor gray = ChatColor.DARK_GRAY;

        List<String> partitionedBackStory = partition(backstory, 45, true);
        List<String> partitionedAbilityDescription = partition(abilityDescription, 50, false);

        lore.add(" ");
        lore.add(gray + "Rarity: " + rarity);
        lore.add(" ");
        lore.addAll(partitionedBackStory);
        lore.add(" ");
        lore.add(gray + "Ability: " + ChatColor.YELLOW + abilityTitle);
        lore.add(gray + "Description: ");
        lore.addAll(partitionedAbilityDescription);
        lore.add(" ");
        lore.add(gray + "Cooldown Duration: " + ChatColor.AQUA + cooldownDuration + gray + " seconds");
        return lore;
    }

    private static List<String> partition(String text, int wrapLength, boolean italics) {
        return Arrays.stream(WordUtils.wrap(text, wrapLength).split("\n")).map(StringUtils::strip).map(s -> ChatColor.GRAY + "" + (italics ? ChatColor.ITALIC : "") + s).collect(Collectors.toList());
    }


}
