package me.ollie.capturethewool.core.command.internal;

import me.ollie.capturethewool.core.command.ICommand;

@FunctionalInterface
public interface InternalSubCommandAdapter {
    <T extends ICommand> InternalSubCommand adapt(T command);
}
