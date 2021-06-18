package me.ollie.capturethewool.core.command.internal;

import lombok.Builder;
import lombok.Getter;
import me.ollie.capturethewool.core.command.ICommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Builder
@Getter
class InternalRootCommand extends Command {

    private final String name;

    private final String permission;

    private final boolean requiresOp;

    private final List<String> aliases;

    private final Map<String, InternalSubCommand> subCommands;

    private final ICommand command;

    private final String description;

    private final String longDescription;

    private final String usageMessage;

    private InternalRootCommand(String name, String permission, boolean requiresOp, List<String> aliases, Map<String, InternalSubCommand> subCommands, ICommand command, String description, String longDescription, String usageMessage) {
        super(name, description, usageMessage, aliases);
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

        if (!hasPermission(sender)) return false;

        if (args.length == 0) { // if args is 0, then execute no args
            command.execute(player, new ArrayList<>());
            return true;
        }

        List<String> argsList = Arrays.asList(args);

        InternalSubCommand subCommand = subCommands.get(argsList.get(0));

        if (subCommand != null) {
            subCommand.getCommand().execute(player, argsList);
            return true;
        }

        command.execute(player, argsList);

        return false;
    }

    private boolean hasPermission(CommandSender sender) {
        return sender.isOp() ||
                ((!sender.isOp() && !requiresOp) &&
                (permission.equals("") || sender.hasPermission(permission)));
    }
}
