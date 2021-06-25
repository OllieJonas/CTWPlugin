package me.ollie.capturethewool.core.command.meta.internal;

import lombok.Builder;
import lombok.Getter;
import me.ollie.capturethewool.core.command.meta.ISubCommand;

import java.util.List;

@Builder
@Getter
public class InternalSubCommand {
    private final String name;

    private final String description;

    private final String longDescription;

    private final String usageMessage;

    private final String permission;

    private final boolean requiresOp;

    private final List<String> aliases;

    private final ISubCommand command;

    private final boolean hideFromHelp;

    private final boolean hideFromUsage;
}
