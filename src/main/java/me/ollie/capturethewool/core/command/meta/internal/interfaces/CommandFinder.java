package me.ollie.capturethewool.core.command.meta.internal.interfaces;

import me.ollie.capturethewool.core.command.meta.IRootCommand;
import me.ollie.capturethewool.core.command.meta.ISubCommand;
import me.ollie.capturethewool.core.command.meta.internal.ICommand;

import java.util.Collection;

public interface CommandFinder {

    Collection<Class<? extends ICommand>> findAll();

    Collection<Class<? extends IRootCommand>> findRoots();

    Collection<Class<? extends ISubCommand>> findSubs();
}
