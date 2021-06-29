package me.ollie.capturethewool.dungeon.shortcut;

import me.ollie.capturethewool.core.util.region.Region;
import org.bukkit.Location;

public class Shortcut {

    private Region shortcut;

    private boolean isLocked = true;

    public void open() {
        this.isLocked = false;
    }
}
