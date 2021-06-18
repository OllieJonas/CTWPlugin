package me.ollie.capturethewool.core.command.internal;

import lombok.Builder;
import lombok.Getter;
import me.ollie.capturethewool.core.command.IRootCommand;
import me.ollie.capturethewool.core.command.internal.context.RootCommandContext;
import me.ollie.capturethewool.core.command.internal.context.SubCommandContext;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;

@Builder
@Getter
public class InternalRootCommand extends Command {

    private final String name;

    private final String permission;

    private final boolean requiresOp;

    private final List<String> aliases;

    private final Map<String, InternalSubCommand> subCommands;

    private final IRootCommand command;

    private final String description;

    private final String longDescription;

    private final String usageMessage;

    private InternalRootCommand(String name, String permission, boolean requiresOp, List<String> aliases, Map<String, InternalSubCommand> subCommands, IRootCommand command, String description, String longDescription, String usageMessage) {
        super(name, description, usageMessage, aliases);
        setLabel(name);
        this.name = name;
        this.permission = permission;
        this.requiresOp = requiresOp;
        this.aliases = aliases;
        this.subCommands = subCommands;
        this.command = command;
        this.description = description;
        this.longDescription = longDescription;
        this.usageMessage = usageMessage;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (!(sender instanceof Player player)) return false;

        if (!CommandUtils.hasPermission(sender, permission, requiresOp)) return noPermission(sender);

        if (args.length == 0) { // if args is 0, then execute no args
            command.execute(player, new RootCommandContext(commandLabel, new ArrayList<>()));
            return true;
        }

        List<String> argsList = Arrays.asList(args);

        InternalSubCommand subCommand = subCommands.get(argsList.get(0).toLowerCase(Locale.ROOT));

        if (subCommand != null) {

            if (!CommandUtils.hasPermission(sender, subCommand.getPermission(), subCommand.isRequiresOp())) return noPermission(sender);

            List<String> subCommandArgsList = argsList.subList(1, argsList.size());
            subCommand.getCommand().execute(player, new SubCommandContext(commandLabel, argsList.get(0), this, subCommandArgsList));
            return true;
        }

        command.execute(player, new RootCommandContext(commandLabel, argsList));

        return false;
    }

    private boolean noPermission(CommandSender sender) {
        sender.sendMessage(ChatColor.RED + "You don't have permission for this!");
        return false;
    }
}
