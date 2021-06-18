package me.ollie.capturethewool.core.game.kit;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public record Kit(String name, Material icon,
                  List<ItemStack> items) {}
