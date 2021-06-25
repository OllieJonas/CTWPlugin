package me.ollie.capturethewool.items.swords;

import me.ollie.capturethewool.Main;
import me.ollie.capturethewool.core.cooldown.progress.ProgressBar;
import me.ollie.capturethewool.core.cooldown.progress.ProgressBarManager;
import me.ollie.capturethewool.core.gui.GUIManager;
import me.ollie.capturethewool.core.gui.PlayerListGUI;
import me.ollie.capturethewool.items.ItemRarity;
import me.ollie.capturethewool.items.meta.AbilityInformation;
import me.ollie.capturethewool.items.types.PowerfulSword;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AssassinsBlade extends PowerfulSword implements Listener {

    private static final Set<Player> currentlyUsing = new HashSet<>();

    private static final Map<Player, Entity> targets = new HashMap<>();

    public AssassinsBlade() {
        super("Assassin's Blade", "", Material.WOODEN_SWORD, ItemRarity.EXOTIC, AbilityInformation.of(
                "Into the Shadows",
                "Choose a player to teleport to. Gain invisibility until you next hit a player." +
                        " You will be teleported to your original location after &e15 &7seconds." +
                        " \n\nThis sword deals &e12&7 damage whilst invisible, regardless of any armour. \n\n If you are wearing armour, the target will be able to see it.", 180F));
    }

    @Override
    public void onInteract(PlayerInteractEvent event) {
        GUIManager.getInstance().openGuiFor(event.getPlayer(), new PlayerListGUI("Click to Teleport (Press ESC to Exit)",
                Bukkit::getOnlinePlayers, // change this
                p -> new String[]{ ChatColor.GRAY + "Click to assassinate " + ChatColor.AQUA + p.getName() },
                this::uponUsing).getGui());
    }

    private void uponUsing(Player player, Player target) {
        Location original = player.getLocation().clone();
        currentlyUsing.add(player);
        player.teleport(target.getLocation());
        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 15 * 20, 5));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 15 * 20, 0));

        ProgressBar progressBar = new ProgressBar(Main.getInstance(), player, "Time Until Return", 15F).override(true).countUp(false).readyMessage(false);

        ProgressBarManager.getInstance().addAndShow(player, progressBar);

        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
            Entity hitTarget = targets.get(player);
            if (hitTarget != null) {
                hitTarget.sendMessage(ChatColor.AQUA + player.getName() + ChatColor.GRAY + " has returned now - you can rest easy.");
            }
            player.sendMessage(ChatColor.AQUA + "You have returned.");
            player.teleport(original);
            currentlyUsing.remove(player);
        }, 15 * 20);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDamage(EntityDamageByEntityEvent event) {
        Entity entity = event.getDamager();
        if (!(entity instanceof Player player)) return;

        Entity target = event.getEntity();

        if (!(target instanceof LivingEntity livingTarget)) return;

        if (!currentlyUsing.contains(player)) return;

        currentlyUsing.remove(player);

        if (ChatColor.stripColor(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName()).equals(getItem().name())) {
            livingTarget.setHealth(Math.max(livingTarget.getHealth() - 12, 0));
        }

        target.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "You have been struck with the Assassin's Blade by " + ChatColor.YELLOW + "" + ChatColor.BOLD + player.getName() + ChatColor.RED + "" + ChatColor.BOLD + "!");
        player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "You have broken invisibility!");

        targets.put(player, target);

        target.getWorld().playSound(target.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 1, 1);
        player.removePotionEffect(PotionEffectType.INVISIBILITY);
    }

}
