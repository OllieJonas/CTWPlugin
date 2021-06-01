package me.ollie.capturethewool.items.swords;

import me.ollie.capturethewool.core.cooldown.CooldownType;
import me.ollie.capturethewool.items.meta.AbilityInformation;
import me.ollie.capturethewool.items.ItemRarity;
import me.ollie.capturethewool.items.types.PowerfulSword;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

public class AspectOfTheEndSword extends PowerfulSword {

    public AspectOfTheEndSword() {
        super("Aspect of the End", "According to all known laws of aviation, there's no a way bee should be able to fly. Its wings are too small to get its fat little body off the ground.", Material.DIAMOND_SWORD, ItemRarity.LEGENDARY,
                AbilityInformation.of(
                        "Instant Teleportation",
                        "Right Click to teleport a certain distance ahead of you.",
                        3.0F));
    }

    @Override
    public CooldownType cooldownType() {
        return CooldownType.INTERACT;
    }

    @Override
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Vector direction = player.getLocation().getDirection();
        Location newLocation = player.getLocation().add(direction.multiply(6));
        if (!isSafeLocation(newLocation)) {
            player.sendMessage(ChatColor.RED + "Not safe location!");
        } else {
            player.teleport(newLocation);
        }
    }

    private static boolean isSafeLocation(Location location) {
        try {
            Block feet = location.getBlock();
            if (!feet.getType().isTransparent() && !feet.getLocation().add(0, 1, 0).getBlock().getType().isTransparent()) {
                return false;
            }
            Block head = feet.getRelative(BlockFace.UP);
            return head.getType().isTransparent();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
