package me.ollie.capturethewool.core.command.internal;

import me.ollie.capturethewool.core.command.ICommand;
import me.ollie.capturethewool.core.command.annotations.CommandAliases;
import me.ollie.capturethewool.core.command.annotations.CommandInfo;
import me.ollie.capturethewool.core.command.annotations.CommandPermissions;
import me.ollie.capturethewool.core.command.annotations.OpCommand;

import java.util.Arrays;
import java.util.List;

import static me.ollie.capturethewool.core.command.internal.CommandUtils.*;

public class InternalSubCommandAdapterImpl implements InternalSubCommandAdapter {

    @SuppressWarnings("DuplicatedCode")
    @Override
    public <T extends ICommand> InternalSubCommand adapt(T command) {
        Class<? extends ICommand> clazz = command.getClass();
        InternalSubCommand.InternalSubCommandBuilder builder = InternalSubCommand.builder();

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

        // command
        builder.command(command);

        return builder.build();
    }
}
