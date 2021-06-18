package me.ollie.capturethewool.core.command.internal.interfaces;

import me.ollie.capturethewool.core.command.IRootCommand;
import me.ollie.capturethewool.core.command.ISubCommand;
import me.ollie.capturethewool.core.command.internal.ICommand;

import java.util.Collection;

public interface CommandFinder {

    Collection<Class<? extends ICommand>> findAll();

    Collection<Class<? extends IRootCommand>> findRoots();

    Collection<Class<? extends ISubCommand>> findSubs();
}
