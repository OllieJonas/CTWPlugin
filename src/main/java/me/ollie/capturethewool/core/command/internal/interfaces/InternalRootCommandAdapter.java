package me.ollie.capturethewool.core.command.internal.interfaces;

import me.ollie.capturethewool.core.command.IRootCommand;
import me.ollie.capturethewool.core.command.internal.ICommand;
import me.ollie.capturethewool.core.command.internal.InternalRootCommand;
import me.ollie.capturethewool.core.command.internal.impl.InternalRootCommandAdapterImpl;

public interface InternalRootCommandAdapter {
    <T extends IRootCommand> InternalRootCommand adapt(T root);
}
