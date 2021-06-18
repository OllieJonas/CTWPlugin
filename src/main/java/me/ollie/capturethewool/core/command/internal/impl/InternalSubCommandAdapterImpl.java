package me.ollie.capturethewool.core.command.internal.impl;

import me.ollie.capturethewool.core.command.ISubCommand;
import me.ollie.capturethewool.core.command.annotations.*;
import me.ollie.capturethewool.core.command.common.HelpCommand;
import me.ollie.capturethewool.core.command.common.UsageCommand;
import me.ollie.capturethewool.core.command.internal.InternalSubCommand;
import me.ollie.capturethewool.core.command.internal.interfaces.InternalSubCommandAdapter;

import java.util.Arrays;
import java.util.List;

import static me.ollie.capturethewool.core.command.internal.CommandUtils.*;

public class InternalSubCommandAdapterImpl implements InternalSubCommandAdapter {

    @SuppressWarnings("DuplicatedCode")
    @Override
    public <T extends ISubCommand> InternalSubCommand adapt(T command) {
        Class<? extends ISubCommand> clazz = command.getClass();
        InternalSubCommand.InternalSubCommandBuilder builder = InternalSubCommand.builder();

        // name
        String name = getAnnotation(clazz, SubCommand.class).map(SubCommand::name).orElseThrow(() -> new IllegalStateException(clazz.getSimpleName() + " must have @SubCommand annotation!"));
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

        // hide from help
        boolean hideFromHelp = getAnnotation(clazz, HelpCommand.Exclude.class).isPresent();
        builder.hideFromHelp(hideFromHelp);

        // hide from usage
        boolean hideFromUsage = getAnnotation(clazz, UsageCommand.Exclude.class).isPresent();
        builder.hideFromUsage(hideFromUsage);

        // command
        builder.command(command);



        return builder.build();
    }
}
