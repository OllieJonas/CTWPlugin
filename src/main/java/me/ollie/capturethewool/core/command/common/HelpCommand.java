package me.ollie.capturethewool.core.command.common;

import me.ollie.capturethewool.core.command.AllCommands;
import me.ollie.capturethewool.core.command.ISubCommand;
import me.ollie.capturethewool.core.command.annotations.CommandInfo;
import me.ollie.capturethewool.core.command.annotations.CommandPermissions;
import me.ollie.capturethewool.core.command.annotations.SubCommand;
import me.ollie.capturethewool.core.command.internal.CommandUtils;
import me.ollie.capturethewool.core.command.internal.InternalSubCommand;
import me.ollie.capturethewool.core.command.internal.context.SubCommandContext;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.*;

@SubCommand(name = "help", root = AllCommands.class)
@CommandPermissions("olscore.help")
@CommandInfo(
        usage = "help <pageno>",
        shortDescription = "List all subcommands for a given command"
)
public class HelpCommand implements ISubCommand {

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface Exclude {}

    private static final int PAGE_SIZE = 2;

    private static final String BORDER = "-";

    private static final ChatColor BORDER_COLOUR = ChatColor.DARK_GRAY;

    private static final ChatColor HELP_COLOUR = ChatColor.AQUA;


    @Override
    public void execute(Player player, SubCommandContext context) {
        List<String> args = context.args();
        int size = context.parent().getSubCommands().size();
        int maxPages = size / PAGE_SIZE + 1;

        int page = args.size() == 0 ? 1 : getPageFrom(args.get(0), player, maxPages);

        if (page == -1) return;

        Map<String, InternalSubCommand> commandMap = new TreeMap<>(context.parent().getSubCommands());

        Set<String> alreadyFoundCommands = new HashSet<>(); // internal

        List<Map.Entry<String, InternalSubCommand>> validCommands = commandMap.entrySet().stream()
                .filter(e -> !alreadyFoundCommands.contains(e.getValue().getName()))
                .filter(e -> !e.getValue().isHideFromHelp())
                .peek(e -> alreadyFoundCommands.add(e.getValue().getName()))
                .filter(e -> CommandUtils.hasPermission(player, e.getValue().getPermission(), e.getValue().isRequiresOp())) // no permission
                .toList();

        int from = (page - 1) * PAGE_SIZE;
        int to = Math.min(size - 1, from + PAGE_SIZE);

        List<Map.Entry<String, InternalSubCommand>> window = validCommands.subList(from, to);

        player.sendMessage(buildHeader(context.rootCommandAlias(), page, maxPages));
        window.forEach(e -> player.sendMessage(ChatColor.DARK_AQUA + "/" + e.getValue().getName() + ChatColor.DARK_GRAY + " - " + ChatColor.AQUA + e.getValue().getDescription()));
        // player.sendMessage(buildFooter());
    }

    private int getPageFrom(String s, Player player, int maxPages) {
        try {
            return Math.min(Integer.parseInt(s), maxPages);
        } catch (NumberFormatException ignored) {
            player.sendMessage(ChatColor.RED + "Please enter a number!");
            return -1;
        }
    }

    private String buildHeader(String commandName, int page, int maxPages) {
        String border = BORDER_COLOUR + BORDER.repeat(10);
        return border + HELP_COLOUR + " /" + commandName + " help (" + page + " / " + maxPages + ") " + border;
    }

    private String buildFooter() {
        return BORDER_COLOUR + BORDER.repeat(30);
    }
}
