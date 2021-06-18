package me.ollie.capturethewool.core.command.internal;

import me.ollie.capturethewool.core.command.ICommand;

import java.util.Collection;

@FunctionalInterface
public interface InternalRootCommandAdapter {
    <T extends ICommand> InternalRootCommand adapt(T root);
}
