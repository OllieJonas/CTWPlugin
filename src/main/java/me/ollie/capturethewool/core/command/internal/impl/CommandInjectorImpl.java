package me.ollie.capturethewool.core.command.internal.impl;

import me.ollie.capturethewool.core.command.internal.interfaces.CommandInjector;
import me.ollie.capturethewool.core.command.internal.InternalRootCommand;
import org.bukkit.Bukkit;

import java.util.Collection;
import java.util.Objects;

public class CommandInjectorImpl implements CommandInjector {

    @Override
    public void inject(String pluginName, Collection<InternalRootCommand> commands) {
        commands.stream().filter(Objects::nonNull).peek(c -> System.out.println(c.getName())).forEach(c -> Bukkit.getCommandMap().register(c.getName(), pluginName, c));
    }
}
