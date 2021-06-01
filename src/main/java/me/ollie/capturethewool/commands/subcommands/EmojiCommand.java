package me.ollie.capturethewool.commands.subcommands;

import me.ollie.capturethewool.core.command.SubCommand;
import me.ollie.capturethewool.core.image.ImageRenderer;
import me.ollie.capturethewool.core.image.ImageRepository;
import me.ollie.capturethewool.core.util.LocationUtil;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Objects;

public class EmojiCommand extends SubCommand {

    public EmojiCommand() {
        super("emoji", true);
    }

    @Override
    public void execute(Player player, String aliasUsed, List<String> args) {
        if (args.size() != 1) return;

        String arg = args.get(0);

        ImageRenderer renderer = ImageRepository.Factory.get(arg);

        if (renderer == null) return;

        renderer.render(LocationUtil.behind(player, 2).add(Objects.requireNonNull(ImageRepository.Factory.getTranslation(arg))));
    }
}
