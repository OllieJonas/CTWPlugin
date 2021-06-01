package me.ollie.capturethewool.core;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import lombok.Getter;
import me.ollie.capturethewool.core.cooldown.CooldownManager;
import me.ollie.capturethewool.core.gui.GUIEvents;
import me.ollie.capturethewool.core.gui.GUIManager;
import me.ollie.capturethewool.core.lobby.LobbyEvents;
import me.ollie.capturethewool.core.lobby.LobbyManager;
import me.ollie.capturethewool.core.potion.PotionEvents;
import me.ollie.capturethewool.core.util.HealthDisplay;
import me.ollie.capturethewool.core.util.HolographicDamageListener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class GamesCore {

    private static GamesCore instance;

    private final ProtocolManager protocolManager;

    private final JavaPlugin plugin;

    private final LobbyManager lobbyManager;

    private final HolographicDamageListener holographicDamageListener;

    public GamesCore(JavaPlugin plugin, ProtocolManager manager) {
        this.plugin = plugin;
        this.protocolManager = manager;
        System.out.println(protocolManager == null);
        this.lobbyManager = new LobbyManager(plugin);
        this.holographicDamageListener = new HolographicDamageListener(plugin);
        new CooldownManager(this);

        new HealthDisplay(plugin);

        registerEvents();

        instance = this;
    }



    private void registerEvents() {
        PluginManager manager = plugin.getServer().getPluginManager();
        manager.registerEvents(new LobbyEvents(lobbyManager), plugin);
        manager.registerEvents(new PotionEvents(), plugin);
        manager.registerEvents(holographicDamageListener, plugin);
        manager.registerEvents(new GUIEvents(GUIManager.getInstance()), plugin);
    }

    public static GamesCore getInstance() {
        return instance;
    }
}
