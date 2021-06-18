package me.ollie.capturethewool.core.command.internal.interfaces;

import me.ollie.capturethewool.core.command.internal.InternalRootCommand;

import java.util.Collection;

@FunctionalInterface
public interface CommandInjector {

    void inject(String pluginName, Collection<InternalRootCommand> commands);
}
