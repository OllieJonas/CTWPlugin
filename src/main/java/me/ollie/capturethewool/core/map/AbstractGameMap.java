package me.ollie.capturethewool.core.map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.World;

@Getter
@AllArgsConstructor
public abstract class AbstractGameMap {

    protected final String name;

    protected final World world;

    public AbstractGameMap(World world) {
        this.name = world.getName();
        this.world = world;
    }

}
