package me.ollie.capturethewool.core;

import com.comphenix.protocol.ProtocolManager;
import lombok.Getter;
import me.ollie.capturethewool.core.command.internal.CommandManager;
import me.ollie.capturethewool.core.cooldown.CooldownManager;
import me.ollie.capturethewool.core.gui.GUIEvents;
import me.ollie.capturethewool.core.gui.GUIManager;
import me.ollie.capturethewool.core.lobby.LobbyEvents;
import me.ollie.capturethewool.core.lobby.LobbyItems;
import me.ollie.capturethewool.core.lobby.LobbyManager;
import me.ollie.capturethewool.core.potion.PotionEvents;
import me.ollie.capturethewool.core.projectile.SimpleProjectileSelectionStrategy;
import me.ollie.capturethewool.core.projectile.SpecialArrowListener;
import me.ollie.capturethewool.core.projectile.SpecialProjectileRegistry;
import me.ollie.capturethewool.core.pve.boss.BossManager;
import me.ollie.capturethewool.core.world.ConstantTime;
import me.ollie.capturethewool.core.world.ConstantWeather;
import me.ollie.capturethewool.core.util.HealthDisplay;
import me.ollie.capturethewool.core.util.HolographicDamageListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class GamesCore {

    private static GamesCore instance;

    private final CommandManager commandManager;

    private final ProtocolManager protocolManager;

    private final JavaPlugin plugin;

    private final LobbyManager lobbyManager;

    private final TimerTask timerTask;

    private final int timerTaskId;

    private final HolographicDamageListener holographicDamageListener;

    private SpecialArrowListener specialArrowListener;

    private final ConstantTime constantTime;

    private final ConstantWeather constantWeather;

    public GamesCore(JavaPlugin plugin, ProtocolManager manager) {
        this.plugin = plugin;
        this.protocolManager = manager;
        this.lobbyManager = new LobbyManager(plugin);
        this.commandManager = new CommandManager(plugin);
        commandManager.init();

        this.timerTask = new TimerTask();
        this.timerTaskId = Bukkit.getScheduler().scheduleAsyncRepeatingTask(this.getPlugin(), timerTask, 0L, 2L);

        this.holographicDamageListener = new HolographicDamageListener(plugin);

        new CooldownManager(this);

        new BossManager(plugin);
        new HealthDisplay(plugin);

        this.constantTime = new ConstantTime(plugin);
        this.constantWeather = new ConstantWeather(plugin);

        registerEvents();

        instance = this;
    }

    public void init() {
        constantTime.start();
        constantWeather.start();
    }

    public void onDisable() {
        constantTime.cancel();
    }

    private void registerEvents() {
        PluginManager manager = plugin.getServer().getPluginManager();
        manager.registerEvents(new LobbyEvents(lobbyManager), plugin);
        manager.registerEvents(new LobbyItems(), plugin);
        manager.registerEvents(new PotionEvents(), plugin);
        manager.registerEvents(holographicDamageListener, plugin);
        manager.registerEvents(new GUIEvents(GUIManager.getInstance()), plugin);
    }

    public void setSpecialProjectileRegistry(SpecialProjectileRegistry registry) {
        this.specialArrowListener = new SpecialArrowListener(plugin, registry, new SimpleProjectileSelectionStrategy(registry));
        plugin.getServer().getPluginManager().registerEvents(specialArrowListener, plugin);
    }

    public static GamesCore getInstance() {
        return instance;
    }

    public long getCounter() {
        return timerTask.getCounter();
    }
}
