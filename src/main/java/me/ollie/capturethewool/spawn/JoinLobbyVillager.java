package me.ollie.capturethewool.spawn;

import me.ollie.capturethewool.core.game.AbstractGame;
import me.ollie.capturethewool.core.gui.GUIManager;
import me.ollie.capturethewool.core.lobby.LobbyGUI;
import me.ollie.capturethewool.core.lobby.LobbyManager;
import me.ollie.capturethewool.core.npc.InteractableVillager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Villager;
import org.bukkit.plugin.java.JavaPlugin;

public class JoinLobbyVillager {

    private InteractableVillager villager;

    public JoinLobbyVillager(JavaPlugin plugin, LobbyManager manager, Class<? extends AbstractGame> clazz, Location location) {
        this.villager = buildVillager(plugin, manager, clazz, location);
    }

    private InteractableVillager buildVillager(JavaPlugin plugin, LobbyManager manager, Class<? extends AbstractGame> clazz, Location location) {
        return new InteractableVillager(plugin,
                location,
                Villager.Profession.NONE,
                ChatColor.AQUA + "" + ChatColor.BOLD + "Capture the Wool", ChatColor.YELLOW + "Click to join!",
                p -> GUIManager.getInstance().openGuiFor(p, new LobbyGUI(manager, p, clazz)));
    }
}
