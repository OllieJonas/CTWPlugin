package me.ollie.capturethewool.core.command.internal.interfaces;

import me.ollie.capturethewool.core.command.ISubCommand;
import me.ollie.capturethewool.core.command.internal.ICommand;
import me.ollie.capturethewool.core.command.internal.InternalSubCommand;

@FunctionalInterface
public interface InternalSubCommandAdapter {
    <T extends ISubCommand> InternalSubCommand adapt(T command);
}
