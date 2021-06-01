package me.ollie.capturethewool.core.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract non-sealed class RootCommand extends AbstractCommand implements CommandExecutor {

    private final Map<String, SubCommand> subCommandMap;

    public RootCommand(String name, String... aliases) {
        this(name, "", false, Arrays.asList(aliases));
    }

    public RootCommand(String name, String permission, boolean requiresOp, List<String> aliases) {
        super(name, permission, requiresOp, aliases);
        this.subCommandMap = new HashMap<>();

        addSubCommands();
    }

    public abstract void addSubCommands();

    public void addSubCommand(SubCommand command) {
        subCommandMap.put(command.getName(), command);
        command.getAliases().forEach(alias -> subCommandMap.put(alias, command));
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player))
            return nonPlayerNotSupported(sender);

        if (!hasPermission(sender))
            return noPermission(sender);

        if (args.length == 0)
            return sendHelp(sender);

        SubCommand subCommand = subCommandMap.get(args[0]);

        if (subCommand == null)
            return subCommandNotFound(sender);

        List<String> newArgs = Arrays.asList(Arrays.copyOfRange(args, 1, args.length));

        if (!subCommand.hasPermission(sender))
            return noPermission(sender);

        subCommand.execute((Player) sender, args[0].toLowerCase(), newArgs);
        return false;
    }

    private boolean nonPlayerNotSupported(CommandSender sender) {
        sender.sendMessage("Non-player not supported big sad");
        return false;
    }

    private boolean sendHelp(CommandSender sender) {
        sender.sendMessage("help time uwu ;33");
        return false;
    }

    private boolean noPermission(CommandSender sender) {
        sender.sendMessage(ChatColor.RED + "No Permission!");
        return false;
    }

    private boolean subCommandNotFound(CommandSender sender) {
        sender.sendMessage(ChatColor.RED + "Couldn't find subcommand!");
        return false;
    }
}
