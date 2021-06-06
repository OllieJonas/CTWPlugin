package me.ollie.capturethewool.core.npc;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import me.ollie.capturethewool.core.hologram.meta.HologramBuilder;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class InteractableVillager {

    private static final double Y_TRANSLATION = 2.675;

    private static final Set<Villager> villagers = new HashSet<>();

    private final Villager villager;

    private final Hologram title;

    private final Consumer<Player> onInteract;

    public InteractableVillager(JavaPlugin plugin, Location location, Villager.Profession profession, String title, String subTitle, Consumer<Player> onInteract) {
        this.villager = buildVillager(location, profession);
        this.title = buildTitle(plugin, location, title, subTitle);
        this.onInteract = onInteract;

        plugin.getServer().getPluginManager().registerEvents(new Listener(), plugin);
    }

    public void remove() {
        title.delete();
        villagers.remove(villager);
        villager.remove();
    }

    private Villager buildVillager(Location location, Villager.Profession profession) {
        Villager villager = location.getWorld().spawn(location, Villager.class);
        villager.setAI(false);
        villager.setAdult();
        villager.setProfession(profession);
        villagers.add(villager);
        return villager;
    }

    private Hologram buildTitle(JavaPlugin plugin, Location location, String title, String subtitle) {
        return new HologramBuilder(plugin, location.clone().add(0, Y_TRANSLATION, 0)).line(title).line(subtitle).onTouchDefault((p, l) -> onInteract.accept(p)).build();
    }

    private class Listener implements org.bukkit.event.Listener {

        @EventHandler
        public void onInteract(PlayerInteractAtEntityEvent event) {
            if (event.getRightClicked() instanceof Villager && event.getRightClicked().getEntityId() == villager.getEntityId()) {
                event.setCancelled(true);
                onInteract.accept(event.getPlayer());
            }
        }
    }

    public static void destroyAll() {
        villagers.forEach(Entity::remove);
    }
}
