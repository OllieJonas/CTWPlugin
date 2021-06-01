package me.ollie.capturethewool.core.cooldown.progress;

import lombok.Getter;
import lombok.Setter;
import me.ollie.capturethewool.core.util.MathsUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.function.Function;

public class ProgressBar {

    // functions
    private static final Function<Float, Float> TICKS_TO_SECONDS = tick -> tick / 20;

    // constants
    private static final ChatColor COMPLETED_COLOUR = ChatColor.AQUA;

    private static final ChatColor TO_GO_COLOUR = ChatColor.DARK_GRAY;

    private static final ChatColor COUNT_DOWN_COLOUR = ChatColor.RED;

    private static final float DEFAULT_UPDATE_RATE = 2; // 2 = 0.1 seconds

    private static final String DEFAULT_ICON = String.join("", Collections.nCopies(24, "▌"));

    private JavaPlugin plugin;

    private Player player;

    @Getter
    private float currTime;

    @Getter
    private final String label;

    @Getter
    private final float time;

    private final float updateRate;

    @Getter
    private final long startTime;

    @Getter
    private boolean override = false;

    @Getter
    private boolean countUp = true;

    @Getter
    private boolean shouldSendReadyMessage = true;

    @Getter
    private final List<Runnable> onFinish;

    private int taskId;

    public ProgressBar(JavaPlugin plugin, Player player, float time) {
        this(plugin, player, "Cooldown", time);
    }

    public ProgressBar(JavaPlugin plugin, Player player, String label, float time) {
        this.plugin = plugin;
        this.player = player;
        this.label = label;
        this.time = time;
        this.currTime = 0.0F;
        this.onFinish = new ArrayList<>();
        onFinish.add(() -> ProgressBarManager.getInstance().remove(player, label));
        this.startTime = System.nanoTime();
        this.updateRate = DEFAULT_UPDATE_RATE;
    }

    public ProgressBar currTime(float time) {
        this.currTime = time;
        return this;
    }

    public ProgressBar override(boolean override) {
        this.override = override;
        return this;
    }

    public ProgressBar readyMessage(boolean ready) {
        this.shouldSendReadyMessage = ready;
        return this;
    }

    public ProgressBar countUp(boolean countUp) {
        this.countUp = countUp;
        return this;
    }

    public ProgressBar onFinish(Runnable... runnables) {
        this.onFinish.addAll(Arrays.asList(runnables));
        return this;
    }

    public void run() {
        taskId = Bukkit.getScheduler().scheduleAsyncRepeatingTask(plugin, new ProgressBarTask(), 0L, (long) updateRate);
    }

    public void cancel() {
        Bukkit.getScheduler().cancelTask(taskId);
    }

    private static String buildActionBar(float originalTime, String label, float currTime, boolean countUp) {
        return buildActionBar(originalTime, currTime, DEFAULT_ICON, label, countUp);
    }

    private static String buildActionBar(float originalTime, float currTime, String icon, String label, boolean countUp) {
        StringBuilder builder = new StringBuilder();
        float timeRemaining = originalTime - currTime;
        float percentage = (timeRemaining / originalTime);

        int split = Math.round(percentage * icon.length());

        int directionalSplit = icon.length() - split;

        builder.append(label).append(": ");
        builder.append(countUp ? COMPLETED_COLOUR : COUNT_DOWN_COLOUR).append(icon, 0, directionalSplit);
        builder.append(countUp ? TO_GO_COLOUR : ChatColor.GREEN).append(icon.substring(directionalSplit));
        builder.append(" ").append(COMPLETED_COLOUR).append(MathsUtil.round(timeRemaining, 1));

        return builder.toString();
    }

    private static String readyActionBar(String label) {
        return COMPLETED_COLOUR + label + " Ready!";
    }

    private class ProgressBarTask implements Runnable {

        private final float increment = TICKS_TO_SECONDS.apply(updateRate);

        public ProgressBarTask() {
        }

        @Override
        public void run() {
            if (shouldCancel(currTime)) {
                player.sendActionBar(readyActionBar(label));
                onFinish.forEach(Runnable::run);
                cancel();
                return;
            }

            player.sendActionBar(buildActionBar(time, label, currTime, countUp));

            currTime += increment;

        }
    }

    private boolean shouldCancel(float currTime) {
        return time - currTime <= 0;
    }
}
