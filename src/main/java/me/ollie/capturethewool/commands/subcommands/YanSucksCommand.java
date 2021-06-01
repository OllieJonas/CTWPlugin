package me.ollie.capturethewool.commands.subcommands;

import me.ollie.capturethewool.CaptureTheWool;
import me.ollie.capturethewool.core.command.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

public class YanSucksCommand extends SubCommand {

    public YanSucksCommand() {
        super("yansucks", true, "ys");
    }

    private volatile float lastExecuted;

    @Override
    public void execute(Player player, String aliasUsed, List<String> args) {
        player.sendMessage(ChatColor.AQUA + "yan sucks " + "alias: " + aliasUsed + " args: " + String.join(", ", args));
        player.sendMessage("" + (System.currentTimeMillis() - lastExecuted));
        lastExecuted = System.currentTimeMillis();

        float start = System.nanoTime();

        Bukkit.getScheduler().scheduleSyncDelayedTask(CaptureTheWool.getInstance(), () -> {
            float end = System.nanoTime();
            player.sendMessage("" + (end - start));
        }, 1000L);
    }
}
