package me.ollie.capturethewool.dungeon;

import lombok.AllArgsConstructor;
import lombok.Builder;
import me.ollie.capturethewool.core.util.region.Region;
import me.ollie.capturethewool.dungeon.rooms.Room;
import me.ollie.capturethewool.dungeon.shortcut.Shortcut;
import org.bukkit.ChatColor;

import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@Builder
public class Dungeon {

    private final String name;

    private final ChatColor colour;

    private final Collection<Room> rooms;

    private final Collection<Shortcut> shortcuts;

    private final Region region;
}
