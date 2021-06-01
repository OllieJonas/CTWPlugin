package me.ollie.capturethewool.dungeon.rooms;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jooq.lambda.tuple.Tuple2;

import java.util.function.Consumer;

public abstract class Room {

    private Tuple2<Location, Location> bounds;

    private Consumer<Player> onEnter;

    private Consumer<Player> onLeave;
}
