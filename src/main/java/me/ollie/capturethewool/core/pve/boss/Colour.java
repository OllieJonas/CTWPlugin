package me.ollie.capturethewool.core.pve.boss;

import lombok.Getter;
import me.ollie.capturethewool.core.util.HealthDisplay;
import net.kyori.adventure.bossbar.BossBar;
import org.bukkit.ChatColor;

import java.util.Arrays;
import java.util.stream.Collectors;

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

    public String getTeamName(Boss<?> boss) {
        return getTeamName(boss.getEnemy().getDisplayName());
    }

    public String getTeamName(String bossName) {
        return Arrays.stream(ChatColor.stripColor(HealthDisplay.removeHealth(bossName)).split(" "))
                .map(s -> s.charAt(0))
                .map(Object::toString)
                .collect(Collectors.joining()) + "-" + name;
    }
}
