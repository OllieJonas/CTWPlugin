package me.ollie.capturethewool.core.cooldown.progress;

import lombok.Getter;
import me.ollie.capturethewool.core.cooldown.TimerTask;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class ProgressBarState {

    @Getter
    private final String label;

    @Getter
    private final float time;

    private final float currTime;

    @Getter
    private final long counterTime;

    private final List<Runnable> runnables;

    private final boolean isOverride;

    private final boolean countUp;

    private final boolean shouldSendReadyMessage;

    public ProgressBarState(String label, float currTime, long counterTime, float totalTime, List<Runnable> runnables, boolean isOverride, boolean countUp, boolean shouldSendReadyMessage) {
        this.label = label;
        this.currTime = currTime;
        this.counterTime = counterTime;
        this.time = totalTime;
        this.runnables = runnables;
        this.isOverride = isOverride;
        this.countUp = countUp;
        this.shouldSendReadyMessage = shouldSendReadyMessage;
    }

    public static ProgressBarState from(ProgressBar bar, long counterTime) {
        return new ProgressBarState(bar.getLabel(), bar.getCurrTime(), counterTime, bar.getTime(), bar.getOnFinish(), bar.isOverride(), bar.isCountUp(), bar.isShouldSendReadyMessage());
    }

    public ProgressBar toProgressBar(JavaPlugin plugin, Player player, long newCounterTime) {
        return new ProgressBar(plugin, player, label, time).onFinish(runnables.toArray(new Runnable[0])).currTime(currTime + TimerTask.timeElapsed(newCounterTime, counterTime)).override(isOverride).countUp(countUp).readyMessage(shouldSendReadyMessage);
    }
}
