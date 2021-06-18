package me.ollie.capturethewool.core.command.internal;

import me.ollie.capturethewool.core.command.IRootCommand;
import me.ollie.capturethewool.core.command.internal.impl.CommandFinderImpl;
import me.ollie.capturethewool.core.command.internal.impl.CommandInjectorImpl;
import me.ollie.capturethewool.core.command.internal.impl.InternalRootCommandAdapterImpl;
import me.ollie.capturethewool.core.command.internal.impl.InternalSubCommandAdapterImpl;
import me.ollie.capturethewool.core.command.internal.interfaces.CommandFinder;
import me.ollie.capturethewool.core.command.internal.interfaces.CommandInjector;
import me.ollie.capturethewool.core.command.internal.interfaces.InternalRootCommandAdapter;
import me.ollie.capturethewool.core.util.SetUtils;
import org.bukkit.plugin.java.JavaPlugin;
import org.jooq.lambda.tuple.Tuple2;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class CommandManager {

    private final JavaPlugin plugin;

    private final CommandFinder finder;

    private final InternalRootCommandAdapter adapter;

    private final CommandInjector injector;

    private final Map<String, InternalRootCommand> commands;

    public CommandManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.finder = new CommandFinderImpl(plugin);
        this.adapter = new InternalRootCommandAdapterImpl(finder, new InternalSubCommandAdapterImpl());
        this.injector = new CommandInjectorImpl();
        this.commands = new HashMap<>();
    }

    public CommandManager(JavaPlugin plugin, CommandFinder finder, InternalRootCommandAdapter adapter, CommandInjector injector) {
        this.plugin = plugin;
        this.finder = finder;
        this.adapter = adapter;
        this.injector = injector;
        this.commands = new HashMap<>();
    }

    public void init() {
        Collection<Class<? extends IRootCommand>> rootClasses = finder.findRoots();
        Collection<IRootCommand> roots = rootClasses.stream().map(CommandUtils::newInstance).collect(Collectors.toUnmodifiableSet());
        System.out.println("roots: " + roots.stream().map(r -> r.getClass().getSimpleName()).collect(Collectors.joining(", ")));
        Collection<InternalRootCommand> internalRoots = roots.stream().map(adapter::adapt).collect(Collectors.toUnmodifiableSet());
        injector.inject(plugin.getDescription().getName(), internalRoots);

        Map<String, InternalRootCommand> map = internalRoots.stream()
                .map(i -> new Tuple2<>(SetUtils.union(Set.of(i.getName()), Set.copyOf(i.getAliases())), i)) // map to pair
                .flatMap(tuple -> tuple.v1().stream().map(t -> new Tuple2<>(t, tuple.v2())))
                .collect(Collectors.toUnmodifiableMap(Tuple2::v1, Tuple2::v2));

        this.commands.putAll(map);
    }

    public InternalRootCommand getCommand(String value) {
        return commands.get(value);
    }
}
