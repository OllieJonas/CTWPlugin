package me.ollie.capturethewool.core.command.internal.impl;

import me.ollie.capturethewool.core.command.AllCommands;
import me.ollie.capturethewool.core.command.IRootCommand;
import me.ollie.capturethewool.core.command.annotations.*;
import me.ollie.capturethewool.core.command.internal.*;
import me.ollie.capturethewool.core.command.internal.interfaces.CommandFinder;
import me.ollie.capturethewool.core.command.internal.interfaces.InternalRootCommandAdapter;
import me.ollie.capturethewool.core.command.internal.interfaces.InternalSubCommandAdapter;
import me.ollie.capturethewool.core.util.SetUtils;
import org.jooq.lambda.tuple.Tuple2;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static me.ollie.capturethewool.core.command.internal.CommandUtils.*;

public final record InternalRootCommandAdapterImpl(CommandFinder finder,
                                                   InternalSubCommandAdapter adapter) implements InternalRootCommandAdapter {

    private record InternalCommandInfo(String usage, String shortDescription, String longDescription) {}

    private static final boolean DEFAULT_REQUIRES_OP_VALUE = false;

    private static final String DEFAULT_PERMISSION_VALUE = "";

    private static final InternalCommandInfo DEFAULT_COMMAND_INFO = new InternalCommandInfo("", "", "");

    @SuppressWarnings("DuplicatedCode")
    @Override
    public <T extends IRootCommand> InternalRootCommand adapt(T command) {
        Class<? extends IRootCommand> clazz = command.getClass();

        InternalRootCommand.InternalRootCommandBuilder builder = InternalRootCommand.builder();

        // name
        String name = getAnnotation(clazz, RootCommand.class).map(RootCommand::value)
                .orElseThrow(() -> new IllegalStateException(clazz.getSimpleName() + " must have @RootCommand annotation! " +
                        "(shouldn't see this - should have been filtered out)"));
        builder.name(name);

        // op command
        boolean requiresOp = getAnnotation(clazz, OperatorCommand.class).map(OperatorCommand::value).orElse(DEFAULT_REQUIRES_OP_VALUE);
        builder.requiresOp(requiresOp);

        // aliases
        List<String> aliases = Arrays.stream(getAnnotation(clazz, CommandAliases.class).map(CommandAliases::value).orElse(new String[0])).toList();
        builder.aliases(aliases);

        // permission
        String permission = getAnnotation(clazz, CommandPermissions.class).map(CommandPermissions::value).orElse(DEFAULT_PERMISSION_VALUE);
        builder.permission(permission);

        // command info
        InternalCommandInfo commandInfo = getAnnotation(clazz, CommandInfo.class)
                .map(i -> new InternalCommandInfo(i.usage(), i.shortDescription(), i.longDescription())).orElse(DEFAULT_COMMAND_INFO);

        builder.usageMessage(commandInfo.usage());
        builder.description(commandInfo.shortDescription());
        builder.longDescription(commandInfo.longDescription());

        // subcommand
        Map<String, InternalSubCommand> subCommands = finder.findSubs().stream()
                .filter(c -> Arrays.stream(c.getAnnotation(SubCommand.class).root())
                        .anyMatch(r -> r == AllCommands.class || r == clazz))
                .map(c -> adapter.adapt(newInstance(c)))
                .map(i -> new Tuple2<>(SetUtils.union(Set.of(i.getName()), Set.copyOf(i.getAliases())), i)) // map to pair
                .flatMap(tuple -> tuple.v1().stream().map(t -> new Tuple2<>(t, tuple.v2())))
                .collect(Collectors.toMap(Tuple2::v1, Tuple2::v2));

        // root command
        builder.command(command);

        builder.subCommands(subCommands);
        return builder.build();
    }
}
