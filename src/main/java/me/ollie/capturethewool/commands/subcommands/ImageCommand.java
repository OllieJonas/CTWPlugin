package me.ollie.capturethewool.commands.subcommands;

import me.ollie.capturethewool.CaptureTheWool;
import me.ollie.capturethewool.core.command.SubCommand;
import me.ollie.capturethewool.core.image.ImageLoader;
import me.ollie.capturethewool.core.image.ImageRenderer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;

public class ImageCommand extends SubCommand {

    public ImageCommand() {
        super("image", true);
    }

    @Override
    public void execute(Player player, String aliasUsed, List<String> args) {
        if (args.size() != 1) {
            player.sendMessage(ChatColor.RED + "Invalid args");
            return;
        }

        ImageLoader.load(args.get(0), () -> player.sendMessage(ChatColor.RED + "Couldn't load image!")).ifPresent(i -> {
            Location location = player.getLocation().clone();
            ImageRenderer renderer = new ImageRenderer(i, true);
            renderer.setRemoveWhite(true);
            renderer.render(location.add(0, 10, 0));
        });
    }
}
