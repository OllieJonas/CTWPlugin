package me.ollie.capturethewool.core.command.internal;

import me.ollie.capturethewool.core.command.AllCommands;
import me.ollie.capturethewool.core.command.ICommand;
import me.ollie.capturethewool.core.command.annotations.*;
import org.jooq.lambda.tuple.Tuple2;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static me.ollie.capturethewool.core.command.internal.CommandUtils.*;

public class InternalRootCommandAdapterImpl implements InternalRootCommandAdapter {

    private record InternalCommandInfo(String usage, String shortDescription, String longDescription) {}

    private static final boolean DEFAULT_REQUIRES_OP_VALUE = false;

    private static final String DEFAULT_PERMISSION_VALUE = "";

    private static final InternalCommandInfo DEFAULT_COMMAND_INFO = new InternalCommandInfo("", "", "");

    private final CommandFinder finder;

    private final InternalSubCommandAdapter adapter;

    public InternalRootCommandAdapterImpl(CommandFinder finder, InternalSubCommandAdapter adapter) {
        this.adapter = adapter;
        this.finder = finder;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public InternalRootCommand adapt(ICommand command) {
        Class<? extends ICommand> clazz = command.getClass();
        InternalRootCommand.InternalRootCommandBuilder builder = InternalRootCommand.builder();

        // op command
        boolean requiresOp = getAnnotation(clazz, OpCommand.class).map(OpCommand::value).orElse(DEFAULT_REQUIRES_OP_VALUE);
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
                .map(c -> new Tuple2<>(c.getAnnotation(SubCommand.class).name(), adapter.adapt(newInstance(c))))
                .collect(Collectors.toMap(Tuple2::v1, Tuple2::v2));

        // root command
        builder.command(command);

        builder.subCommands(subCommands);
        return builder.build();
    }
}
