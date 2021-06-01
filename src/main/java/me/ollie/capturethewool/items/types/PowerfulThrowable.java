package me.ollie.capturethewool.items.types;

import me.ollie.capturethewool.items.meta.AbilityInformation;
import me.ollie.capturethewool.items.ItemRarity;
import org.bukkit.Material;
import org.bukkit.event.entity.ProjectileLaunchEvent;

public abstract class PowerfulThrowable extends PowerfulItem {

    public PowerfulThrowable(String name, String backstory, Material material, ItemRarity rarity, AbilityInformation information) {
        super(name, backstory, material, rarity, information);
    }

    public void onThrow(ProjectileLaunchEvent event) {

    }
}
