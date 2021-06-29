package me.ollie.capturethewool.dungeon.rooms;

import me.ollie.capturethewool.core.pve.Spawner;
import me.ollie.capturethewool.core.util.region.Region;
import org.bukkit.entity.Player;

public class MobRoom extends Room {

    public MobRoom(Region region) {
        super(region);
    }

    @Override
    public void onEnter(Player player) {

    }

    @Override
    public void onLeave(Player player) {

    }

    public MobRoom addSpawner(Spawner<?> spawner) {
        spawners.add(spawner);
        return this;
    }

    public MobRoom randomSpawners(int amount) {
        return this;
    }

    @Override
    public void init() {

    }
}
