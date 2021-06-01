package me.ollie.capturethewool.core.command;

import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Getter
public abstract non-sealed class SubCommand extends AbstractCommand {

    public SubCommand(String name) {
        this(name, "", false, Collections.emptyList());
    }

    public SubCommand(String name, String permission) {
        this(name, "", false, Collections.emptyList());
    }

    public SubCommand(String name, String... aliases) {
        this(name, "", false, Arrays.asList(aliases));
    }

    public SubCommand(String name, boolean requiresOp, String... aliases) {
        this(name, "", requiresOp, Arrays.asList(aliases));
    }

    public SubCommand(String name, String permission, boolean requiresOp, List<String> aliases) {
        super(name, permission, requiresOp, aliases);
    }

    public abstract void execute(Player player, String aliasUsed, List<String> args);
}
