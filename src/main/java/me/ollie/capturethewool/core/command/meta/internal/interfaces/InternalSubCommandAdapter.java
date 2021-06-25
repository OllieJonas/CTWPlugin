package me.ollie.capturethewool.core.command.meta.internal.interfaces;

import me.ollie.capturethewool.core.command.meta.ISubCommand;
import me.ollie.capturethewool.core.command.meta.internal.InternalSubCommand;

@FunctionalInterface
public interface InternalSubCommandAdapter {
    <T extends ISubCommand> InternalSubCommand adapt(T command);
}
