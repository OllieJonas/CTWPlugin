package me.ollie.capturethewool.core.pve.boss;

import lombok.Getter;
import net.kyori.adventure.bossbar.BossBar;
import org.bukkit.ChatColor;

@Getter
public enum Colour {

    BLUE("blue", BossBar.Color.BLUE, ChatColor.DARK_AQUA),
    RED("red", BossBar.Color.RED, ChatColor.RED),
    GREEN("green", BossBar.Color.GREEN, ChatColor.GREEN),
    YELLOW("yellow", BossBar.Color.YELLOW, ChatColor.YELLOW),
    PURPLE("purple", BossBar.Color.PURPLE, ChatColor.DARK_PURPLE),
    WHITE("white", BossBar.Color.WHITE, ChatColor.WHITE);

    private final String name;

    private final BossBar.Color bossBarColour;

    private final ChatColor chatColour;

    Colour(String name, BossBar.Color bossBarColour, ChatColor chatColour) {
        this.name = name;
        this.bossBarColour = bossBarColour;
        this.chatColour = chatColour;
    }
}
