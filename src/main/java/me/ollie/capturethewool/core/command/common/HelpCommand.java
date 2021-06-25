package me.ollie.capturethewool.core.command.common;

import me.ollie.capturethewool.core.command.meta.AllCommands;
import me.ollie.capturethewool.core.command.meta.ISubCommand;
import me.ollie.capturethewool.core.command.meta.annotations.CommandInfo;
import me.ollie.capturethewool.core.command.meta.annotations.SubCommand;
import me.ollie.capturethewool.core.command.meta.internal.CommandUtils;
import me.ollie.capturethewool.core.command.meta.internal.InternalSubCommand;
import me.ollie.capturethewool.core.command.meta.internal.context.SubCommandContext;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.*;

@SubCommand(name = "help", root = AllCommands.class)
@CommandInfo(
        usage = "<page number>",
        shortDescription = "List all subcommands for a given command",
        longDescription =
                        """
                        Lists all subcommands for a given root command.\s

                        This will only show commands you have access to.
                                        
                        If you are an operator, any commands that are operator only will be highlighted in red
                        and will appear first in the list, in alphabetical order. Any other commands will also appear in
                        alphabetical order.
                        """
)
public class HelpCommand implements ISubCommand {

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface Exclude {}

    private static final int PAGE_SIZE = 7;

    private static final String BORDER = "-";

    private static final ChatColor BORDER_COLOUR = ChatColor.DARK_GRAY;

    private static final ChatColor HELP_COLOUR = ChatColor.AQUA;

    private static final Comparator<Map.Entry<String, InternalSubCommand>> COMPARATOR = (o1, o2) -> {
        int boolComparison = Boolean.compare(o1.getValue().isRequiresOp(), o2.getValue().isRequiresOp());
        if (boolComparison != 0) return -boolComparison;
        return o1.getValue().getName().compareTo(o2.getValue().getName());
    };


    @Override
    public void execute(Player player, SubCommandContext context) {
        List<String> args = context.args();

        Set<String> alreadyFoundCommands = new HashSet<>(); // internal

        List<Map.Entry<String, InternalSubCommand>> validCommands = context.parent().getSubCommands().entrySet().stream()
                .filter(e -> !alreadyFoundCommands.contains(e.getValue().getName())) // remove duplicates
                .filter(e -> !e.getValue().isHideFromHelp()) // remove those hidden from help
                .peek(e -> alreadyFoundCommands.add(e.getValue().getName())) // add to duplicate set
                .filter(e -> CommandUtils.hasPermission(player, e.getValue())) // do they have permission?
                .sorted(COMPARATOR)
                .toList();

        int size = validCommands.size();
        int maxPages = size / PAGE_SIZE + (size >= PAGE_SIZE ? 0 : 1);

        int page = args.size() == 0 ? 1 : getPageFrom(args.get(0), player, maxPages);
        if (page == -1) return;

        int from = Math.min(size - 1, (page - 1) * PAGE_SIZE);
        int to = Math.min(size - 1, from + PAGE_SIZE);

        List<Map.Entry<String, InternalSubCommand>> window = validCommands.subList(from, to);

        player.sendMessage(buildHeader(context.rootCommandAlias(), page, maxPages));
        window.forEach(e -> player.sendMessage((e.getValue().isRequiresOp() ? ChatColor.RED : ChatColor.DARK_AQUA) + "/" + e.getValue().getName() + ChatColor.DARK_GRAY + " - " + ChatColor.AQUA + e.getValue().getDescription()));
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
