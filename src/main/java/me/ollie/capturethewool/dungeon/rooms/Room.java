package me.ollie.capturethewool.dungeon.rooms;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.function.Consumer;

public abstract class Room {

    private Consumer<Player> onEnter;

    private Consumer<Player> onLeave;
}
