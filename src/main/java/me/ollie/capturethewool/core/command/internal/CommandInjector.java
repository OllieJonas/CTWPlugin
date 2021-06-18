package me.ollie.capturethewool.core.command.internal;

import me.ollie.capturethewool.core.command.ICommand;

import java.util.Collection;

@FunctionalInterface
public interface CommandInjector {

    void inject(Collection<InternalRootCommand> commands);
}
