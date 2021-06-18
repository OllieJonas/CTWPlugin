package me.ollie.capturethewool.core.command.internal;

import org.bukkit.Bukkit;

import java.util.Collection;

public class CommandInjectorImpl implements CommandInjector {

    @Override
    public void inject(Collection<InternalRootCommand> commands) {
        commands.forEach(c -> Bukkit.getCommandMap().register(c.getName(), c));
    }
}
