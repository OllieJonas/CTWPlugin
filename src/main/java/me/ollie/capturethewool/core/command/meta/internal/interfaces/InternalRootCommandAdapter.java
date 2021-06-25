package me.ollie.capturethewool.core.command.meta.internal.interfaces;

import me.ollie.capturethewool.core.command.meta.IRootCommand;
import me.ollie.capturethewool.core.command.meta.internal.InternalRootCommand;

public interface InternalRootCommandAdapter {
    <T extends IRootCommand> InternalRootCommand adapt(T root);
}
