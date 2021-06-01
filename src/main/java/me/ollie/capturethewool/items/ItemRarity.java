package me.ollie.capturethewool.items;

import org.bukkit.ChatColor;

public enum ItemRarity {
    COMMON("Common", ChatColor.WHITE),
    UNCOMMON("Uncommon", ChatColor.GREEN),
    RARE("Rare", ChatColor.DARK_AQUA),
    LEGENDARY("Legendary", ChatColor.DARK_PURPLE),
    EXOTIC("Exotic", ChatColor.GOLD);

    private final String title;

    private final ChatColor colour;

    ItemRarity(String title, ChatColor colour) {
        this.title = title;
        this.colour = colour;
    }

    public String getTitle() {
        return title;
    }

    public ChatColor getColour() {
        return colour;
    }

    public String getFormattedTitle() {
        return colour + "" + ChatColor.BOLD + getTitle();
    }
}
