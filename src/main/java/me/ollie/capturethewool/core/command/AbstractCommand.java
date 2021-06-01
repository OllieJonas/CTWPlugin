package me.ollie.capturethewool.core.command;

import lombok.Getter;
import org.bukkit.command.CommandSender;

import java.util.List;

@Getter
public abstract sealed class AbstractCommand permits RootCommand, SubCommand {

    protected String name;

    protected List<String> aliases;

    protected String permission;

    protected boolean requiresOp;

    public AbstractCommand(String name, String permission, boolean requiresOp, List<String> aliases) {
        this.name = name;
        this.aliases = aliases;
        this.permission = permission;
        this.requiresOp = requiresOp;
    }

    protected boolean hasPermission(CommandSender sender) {
        return (!requiresOp && (!permission.equals("") && sender.hasPermission(permission))) || sender.isOp();
    }
}
