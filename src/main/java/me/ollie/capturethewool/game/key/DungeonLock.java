package me.ollie.capturethewool.game.key;

import org.bukkit.Location;

import java.util.HashMap;
import java.util.Map;

public record DungeonLock(Location location, LockType lockType) {

    private static final Map<Location, DungeonLock> LOCKS = new HashMap<>();

    public static void remove(Location location) {
        DungeonDoor.remove(LOCKS.remove(location));
    }

    public static DungeonLock getLockFrom(Location location) {
        return LOCKS.get(location);
    }

    public void init() {
        location.getBlock().setType(lockType.getMaterial());
        LOCKS.put(location, this);
    }


}
