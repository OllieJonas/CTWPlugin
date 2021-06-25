package me.ollie.capturethewool.core.command.meta.internal.interfaces;

import me.ollie.capturethewool.core.command.meta.internal.InternalRootCommand;

import java.util.Collection;

@FunctionalInterface
public interface CommandInjector {

    void inject(String pluginName, Collection<InternalRootCommand> commands);
}
