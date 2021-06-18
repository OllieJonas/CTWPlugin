package me.ollie.capturethewool.core.command.internal;

import me.ollie.capturethewool.core.command.ICommand;
import me.ollie.capturethewool.core.command.annotations.RootCommand;
import me.ollie.capturethewool.core.command.annotations.SubCommand;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class CommandFinderImpl implements CommandFinder {

    private final Reflections reflections;

    private Set<Class<? extends ICommand>> all;

    private Set<Class<? extends ICommand>> roots;

    private Set<Class<? extends ICommand>> subs;

    public CommandFinderImpl(JavaPlugin plugin) {
        this(Arrays.stream(plugin.getDescription().getMain().split("\\.")).limit(3).collect(Collectors.joining(".")));
    }

    public CommandFinderImpl(String packageName) {
        this(new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forPackage(packageName))
                .setScanners(new TypeAnnotationsScanner())
                .useParallelExecutor()));
    }

    public CommandFinderImpl(Reflections reflections) {
        this.reflections = reflections;
    }

    @Override
    public Collection<Class<? extends ICommand>> findAll() {
        if (all == null) {
            all = reflections.getSubTypesOf(ICommand.class);
        }

        return all;
    }

    @Override
    public Collection<Class<? extends ICommand>> findRoots() {
        if (roots == null) {
            roots = findAll().stream().filter(c -> c.isAnnotationPresent(RootCommand.class)).collect(Collectors.toUnmodifiableSet());
        }
        return roots;
    }

    @Override
    public Collection<Class<? extends ICommand>> findSubs() {
        if (subs == null) {
            subs = findAll().stream().filter(c -> c.isAnnotationPresent(SubCommand.class)).collect(Collectors.toUnmodifiableSet());
        }
        return subs;
    }
}
