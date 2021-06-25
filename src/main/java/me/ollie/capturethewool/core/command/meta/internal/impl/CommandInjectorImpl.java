package me.ollie.capturethewool.core.command.meta.internal.impl;

import me.ollie.capturethewool.core.command.meta.internal.interfaces.CommandInjector;
import me.ollie.capturethewool.core.command.meta.internal.InternalRootCommand;
import org.bukkit.Bukkit;

import java.util.Collection;
import java.util.Objects;

public class CommandInjectorImpl implements CommandInjector {

    @Override
    public void inject(String pluginName, Collection<InternalRootCommand> commands) {
        commands.stream().filter(Objects::nonNull).forEach(c -> Bukkit.getCommandMap().register(c.getName(), pluginName, c));
    }
}
