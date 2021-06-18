package me.ollie.capturethewool.core.command.common;

import me.ollie.capturethewool.core.command.AllCommands;
import me.ollie.capturethewool.core.command.ISubCommand;
import me.ollie.capturethewool.core.command.annotations.CommandInfo;
import me.ollie.capturethewool.core.command.annotations.SubCommand;
import me.ollie.capturethewool.core.command.internal.CommandUtils;
import me.ollie.capturethewool.core.command.internal.InternalSubCommand;
import me.ollie.capturethewool.core.command.internal.context.SubCommandContext;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SubCommand(name = "usage", root = AllCommands.class)
@CommandInfo(
        usage = "<command>",
        shortDescription = "Get more information on how to use a command",
        longDescription = "Gives you more information on how to use a particular subcommand specified. \n\nThis will only show commands that you have access to."
)
public class UsageCommand implements ISubCommand {

    public @interface Exclude {}

    private static final String BORDER = "-";

    private static final ChatColor BORDER_COLOUR = ChatColor.DARK_GRAY;

    private static final ChatColor USAGE_COLOUR = ChatColor.AQUA;

    @Override
    public void execute(Player player, SubCommandContext context) {
        List<String> args = context.args();
        if (args.size() != 1) {
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
        List<String> list = new ArrayList<>();
        list.add(ChatColor.DARK_AQUA + "Name: " + ChatColor.AQUA + command.getName());
        list.add(ChatColor.DARK_AQUA + "Aliases: " + ChatColor.AQUA + String.join(", ", command.getAliases()));
        list.add(ChatColor.DARK_AQUA + "Permission: " + ChatColor.AQUA + (command.getPermission().equals("") ? "N/A" : command.getPermission()));
        list.add(ChatColor.DARK_AQUA + "Usage: " + ChatColor.AQUA + "/" + root + " " + command.getName() + " " + command.getUsageMessage());
        list.add(ChatColor.DARK_AQUA + "Short Description: " + ChatColor.AQUA + command.getDescription());
        list.add(ChatColor.DARK_AQUA + "Long Description: ");
        Arrays.stream(command.getLongDescription().split("\n")).map(l -> ChatColor.AQUA + l).forEach(list::add);
        return list;
    }

    private String buildHeader(String commandName) {
        String border = BORDER_COLOUR + BORDER.repeat(10);
        return border + USAGE_COLOUR + "Usage for /" + commandName + " " + border;
    }
}
