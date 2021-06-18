package me.ollie.capturethewool.core.command.internal;

import me.ollie.capturethewool.core.command.ICommand;

import java.util.Collection;

public interface CommandFinder {

    Collection<Class<? extends ICommand>> findAll();

    Collection<Class<? extends ICommand>> findRoots();

    Collection<Class<? extends ICommand>> findSubs();
}
