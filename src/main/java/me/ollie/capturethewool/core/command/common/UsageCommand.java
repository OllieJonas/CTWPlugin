package me.ollie.capturethewool.core.command.common;

import me.ollie.capturethewool.core.command.meta.AllCommands;
import me.ollie.capturethewool.core.command.meta.ISubCommand;
import me.ollie.capturethewool.core.command.meta.annotations.CommandAliases;
import me.ollie.capturethewool.core.command.meta.annotations.CommandInfo;
import me.ollie.capturethewool.core.command.meta.annotations.SubCommand;
import me.ollie.capturethewool.core.command.meta.internal.CommandUtils;
import me.ollie.capturethewool.core.command.meta.internal.InternalRootCommand;
import me.ollie.capturethewool.core.command.meta.internal.InternalSubCommand;
import me.ollie.capturethewool.core.command.meta.internal.context.SubCommandContext;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SubCommand(name = "usage", root = AllCommands.class)
@CommandAliases({"information", "info"})
@CommandInfo(
        usage = "<command>",
        shortDescription = "Get more information on how to use a command",
        longDescription = "Gives you more information on how to use a particular subcommand specified. \n\nThis will only show commands that you have access to."
)
public class UsageCommand implements ISubCommand {

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface Exclude {}

    private static final String BORDER = "-";

    private static final String TICK = ChatColor.GREEN + "" + ChatColor.BOLD + "✓";

    private static final String CROSS = ChatColor.RED + "" + ChatColor.BOLD + "✖";

    private static final ChatColor BORDER_COLOUR = ChatColor.DARK_GRAY;

    private static final ChatColor USAGE_COLOUR = ChatColor.AQUA;

    @Override
    public void execute(Player player, SubCommandContext context) {
        List<String> args = context.args();

        if (args.size() == 0) {
            player.sendMessage(buildHeader(context.parent().getName()));
            buildUsageMessage(context.parent().getName(), context.parent()).forEach(player::sendMessage);
            return;
        }

        if (args.size() > 1) {
            badUsage(player);
            return;
        }

        InternalSubCommand command = context.parent().getSubCommands().get(args.get(0));

        if (command == null) {
            player.sendMessage(ChatColor.RED + "This command doesn't exist!");
            return;
        }

        if (command.isHideFromUsage()) {
            player.sendMessage(ChatColor.RED + "This command has been hidden from the usage command!");
            return;
        }

        if (!CommandUtils.hasPermission(player, command)) {
            player.sendMessage(ChatColor.RED + "You don't have permission to view the usage of this command!");
            return;
        }

        player.sendMessage(buildHeader(command.getName()));
        buildUsageMessage(context.parent().getName(), command).forEach(player::sendMessage);
    }

    private List<String> buildUsageMessage(String root, InternalSubCommand command) {
        return getStrings(root, command.getName(), command.getAliases(), command.getPermission(), command.isRequiresOp(), command.getUsageMessage(), command.getDescription(), command.getLongDescription(), true);
    }

    private List<String> buildUsageMessage(String root, InternalRootCommand command) {
        return getStrings(root, command.getName(), command.getAliases(), command.getPermission() == null ? "" : command.getPermission(), command.isRequiresOp(), command.getUsageMessage(), command.getDescription(), command.getLongDescription(), false);
    }

    @NotNull
    private List<String> getStrings(String root, String name, List<String> aliases, String permission, boolean requiresOp, String usageMessage, String description, String longDescription, boolean isSub) {
        List<String> list = new ArrayList<>();
        list.add(ChatColor.DARK_AQUA + "Name: " + ChatColor.AQUA + name);
        list.add(ChatColor.DARK_AQUA + "Aliases: " + ChatColor.AQUA + String.join(", ", aliases));
        list.add(ChatColor.DARK_AQUA + "Permission: " + ChatColor.AQUA + (permission.equals("") ? "N/A" : permission));
        list.add(ChatColor.DARK_AQUA + "Requires OP: " + (requiresOp ? TICK : CROSS));

        if (isSub)
            list.add(ChatColor.DARK_AQUA + "Usage: " + ChatColor.AQUA + "/" + root + " " + name + " " + usageMessage);

        list.add(ChatColor.DARK_AQUA + "Short Description: " + ChatColor.AQUA + description);
        list.add(ChatColor.DARK_AQUA + "Long Description: ");
        Arrays.stream(longDescription.split("\n")).map(l -> ChatColor.AQUA + l).forEach(list::add);
        return list;
    }

    private String buildHeader(String commandName) {
        String border = BORDER_COLOUR + BORDER.repeat(10);
        return border + USAGE_COLOUR + " Usage for /" + commandName + " " + border;
    }
}
