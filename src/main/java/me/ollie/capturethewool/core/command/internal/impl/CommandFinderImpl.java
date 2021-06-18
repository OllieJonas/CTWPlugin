package me.ollie.capturethewool.core.command.internal.impl;

import me.ollie.capturethewool.core.command.AllCommands;
import me.ollie.capturethewool.core.command.IRootCommand;
import me.ollie.capturethewool.core.command.ISubCommand;
import me.ollie.capturethewool.core.command.annotations.RootCommand;
import me.ollie.capturethewool.core.command.annotations.SubCommand;
import me.ollie.capturethewool.core.command.internal.ICommand;
import me.ollie.capturethewool.core.command.internal.interfaces.CommandFinder;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CommandFinderImpl implements CommandFinder {

    private static final Set<Class<? extends ICommand>> EXCLUDED = Set.of(IRootCommand.class, ISubCommand.class, AllCommands.class);


    private final Reflections reflections;

    private Set<Class<? extends ICommand>> all;

    private Set<Class<? extends IRootCommand>> roots;

    private Set<Class<? extends ISubCommand>> subs;

    public CommandFinderImpl(JavaPlugin plugin) {
        this(Arrays.stream(plugin.getDescription().getMain().split("\\.")).limit(3).collect(Collectors.joining(".")));
    }

    public CommandFinderImpl(String packageName) {
        this(new Reflections(packageName));
    }

    public CommandFinderImpl(Reflections reflections) {
        this.reflections = reflections;
    }

    @Override
    public Collection<Class<? extends ICommand>> findAll() {
        if (all == null) {
            all = reflections.getSubTypesOf(ICommand.class).stream()
                    .filter(c -> !EXCLUDED.contains(c))
                    .collect(Collectors.toUnmodifiableSet());
            System.out.println(all.stream().map(Class::getSimpleName).collect(Collectors.joining(", ")));
        }

        return all;
    }

    @Override
    public Collection<Class<? extends IRootCommand>> findRoots() {
        if (roots == null) {
            roots = findAll().stream()
                    .filter(IRootCommand.class::isAssignableFrom)
                    .map((Function<Class<? extends ICommand>, Class<? extends IRootCommand>>) c -> c.asSubclass(IRootCommand.class))
                    .filter(c -> c.isAnnotationPresent(RootCommand.class))
                    .collect(Collectors.toUnmodifiableSet());

        }
        return roots;
    }

    @Override
    public Collection<Class<? extends ISubCommand>> findSubs() {
        if (subs == null) {
            subs = findAll().stream()
                    .filter(ISubCommand.class::isAssignableFrom)
                    .map((Function<Class<? extends ICommand>, Class<? extends ISubCommand>>) c -> c.asSubclass(ISubCommand.class))
                    .filter(c -> c.isAnnotationPresent(SubCommand.class))
                    .collect(Collectors.toUnmodifiableSet());
        }
        return subs;
    }
}
