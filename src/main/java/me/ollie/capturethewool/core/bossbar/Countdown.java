package me.ollie.capturethewool.core.bossbar;

import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Countdown {

    private final JavaPlugin plugin;

    private final String message;

    private final AtomicInteger counter = new AtomicInteger(0);

    private final GameBossBar bossBar;

    private final Collection<Player> audience;

    private final Map<Integer, Runnable> actions;

    private final int seconds;

    private final Runnable onFinish;

    private int taskId;

    private String title;

    private boolean displayTitle = true;

    public Countdown(JavaPlugin plugin, String message, Collection<Player> audience, int seconds) {
        this(plugin, message, audience, seconds, () -> {});
    }

    public Countdown(JavaPlugin plugin, String message, Collection<Player> audience, int seconds, Runnable onFinish) {
        this.plugin = plugin;
        this.message = message;
        this.audience = audience;
        this.seconds = seconds;
        this.actions = new HashMap<>();
        this.bossBar = buildBossBar(message, seconds);
        this.onFinish = onFinish;
    }

    private static String addTitle(String title, String message, int timeRemaining) {
        return (title != null ? ChatColor.DARK_AQUA + "" + ChatColor.BOLD + title + ChatColor.DARK_GRAY + " | " : "") + formatCountdown(message, timeRemaining);
    }

    private static String formatCountdown(String message, int timeRemaining) {
        return ChatColor.GRAY + message + ChatColor.AQUA + toMinutesAndSeconds(timeRemaining);
    }

    private static String toMinutesAndSeconds(int timeRemaining) {
        return timeRemaining < 60 ? getSeconds(timeRemaining) : getMinutes(timeRemaining / 60) + " " + getSeconds(timeRemaining % 60);
    }

    private static String getSeconds(int time) {
        return time + " " + (time == 1 ? "second" : "seconds");
    }

    private static String getMinutes(int time) {
        return time + " " + (time == 1 ? "minute" : "minutes");
    }

    public void addPlayer(Player player) {
        audience.add(player);
        if (bossBar != null)
            bossBar.showTo(player);
    }

    public Countdown withAction(int time, Runnable action) {
        actions.put(time, action);
        return this;
    }

    public Countdown setTitle(String title) {
        this.title = title;
        return this;
    }

    public Countdown setDisplaySubtitle(boolean displayTitle) {
        this.displayTitle = displayTitle;
        return this;
    }

    public void removePlayer(Player player) {
        audience.remove(player);
        if (bossBar != null)
            bossBar.hideFrom(player);
    }

    public Countdown start() {
        bossBar.run();
        audience.forEach(bossBar::showTo);
        taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Task(), 0L, 20L);
        return this;
    }

    public void interrupt() {
        Bukkit.getScheduler().cancelTask(taskId);
    }

    public void destroy() {
        Bukkit.getScheduler().cancelTask(taskId);
        bossBar.destroy();
    }

    private GameBossBar buildBossBar(String message, int seconds) {
        return new GameBossBar(plugin, 1, TimeUnit.SECONDS,
                IntStream.rangeClosed(0, seconds - 1)
                        .boxed()
                        .map(i -> BossBar.bossBar(Component.text(formatCountdown(message, seconds - i)), 1.0F - ((float) i / (float) seconds), BossBar.Color.PURPLE, BossBar.Overlay.PROGRESS)).collect(Collectors.toList()));
    }

    private class Task implements Runnable {

        private boolean cancelled = false;

        @Override
        public void run() {

            if (cancelled) {
                destroy();
                onFinish.run();
                return;
            }

            int secondsRemaining = seconds - counter.get();

            if (actions.containsKey(secondsRemaining))
                actions.get(secondsRemaining).run();

            if (secondsRemaining == seconds || secondsRemaining == 15) {
                sendNotificationToAll(secondsRemaining);
            }


            if (secondsRemaining <= 1) {
                displayFinalCountdown(secondsRemaining, ChatColor.RED);
                cancelled = true;
            }
            else if (secondsRemaining < 4)
                displayFinalCountdown(secondsRemaining, ChatColor.YELLOW);
            else if (secondsRemaining < 6)
                displayFinalCountdown(secondsRemaining, ChatColor.GREEN);

            counter.getAndIncrement();
        }

        private void displayFinalCountdown(int secondsRemaining, ChatColor red) {
            audience.forEach(p -> {
                p.sendMessage(addTitle(title, message, secondsRemaining));
                if (displayTitle)
                    p.showTitle(Title.title(Component.text(red + "" + secondsRemaining), Component.text("")));
            });
        }

        private void sendNotificationToAll(int timeRemaining) {
            audience.forEach(p -> {
                if (displayTitle)
                    p.sendTitle("", formatCountdown(message, timeRemaining));

                p.sendMessage(addTitle(title, message, timeRemaining));
            });
        }
    }
}
