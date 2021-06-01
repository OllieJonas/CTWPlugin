package me.ollie.capturethewool.core.game.team;

import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.List;

@Getter
public enum TeamColour {
    GREEN("Green", ChatColor.GREEN, Material.GREEN_WOOL),
    BLUE("Blue", ChatColor.AQUA, Material.BLUE_WOOL),
    RED("Red", ChatColor.RED, Material.RED_WOOL),
    YELLOW("Yellow", ChatColor.YELLOW, Material.YELLOW_WOOL),
    PINK("Pink", ChatColor.LIGHT_PURPLE, Material.PINK_WOOL),
    WHITE("White", ChatColor.WHITE, Material.WHITE_WOOL),
    PURPLE("Purple", ChatColor.DARK_PURPLE, Material.PURPLE_WOOL),
    GRAY("Gray", ChatColor.GRAY, Material.GRAY_WOOL);


    private static final TeamColour[] values = TeamColour.values();

    private final String name;

    private final ChatColor chatColor;

    private final Material icon;

    TeamColour(String name, ChatColor chatColor, Material icon) {
        this.name = name;
        this.chatColor = chatColor;
        this.icon = icon;
    }

    public static List<TeamColour> getFirstNTeams(int n) {
        return Arrays.asList(Arrays.copyOfRange(values, 0, n));
    }
}
