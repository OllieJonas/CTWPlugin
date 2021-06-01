package me.ollie.capturethewool.commands.subcommands;

import me.ollie.capturethewool.CaptureTheWool;
import me.ollie.capturethewool.core.command.SubCommand;
import me.ollie.capturethewool.core.cooldown.progress.ProgressBar;
import me.ollie.capturethewool.core.cooldown.progress.ProgressBarManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class ProgressBarCommand extends SubCommand {

    public ProgressBarCommand() {
        super("progressbar", true);
    }

    @Override
    public void execute(Player player, String aliasUsed, List<String> args) {
        float time = args.size() == 0 ? 10 : Float.parseFloat(args.get(0));
        String label = "X Seconds";
        String label2 = "3 Seconds";


        ProgressBar tenSeconds = new ProgressBar(CaptureTheWool.getInstance(), player, label, time).override(true).countUp(false);
        ProgressBar threeSeconds = new ProgressBar(CaptureTheWool.getInstance(), player, label2, 3L).onFinish(() -> ProgressBarManager.getInstance().show(player, "X Seconds"));

        ProgressBarManager.getInstance().addAndShow(player, tenSeconds);

        Bukkit.getScheduler().scheduleAsyncDelayedTask(CaptureTheWool.getInstance(),() -> ProgressBarManager.getInstance().addAndShow(player, threeSeconds), 60L);

        Bukkit.getScheduler().scheduleSyncDelayedTask(CaptureTheWool.getInstance(), () -> {
            float end = System.nanoTime();
            player.sendMessage("10s");
        }, 10 * 20);
    }
}
