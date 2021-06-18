package me.ollie.capturethewool.core.command.internal.context;

import me.ollie.capturethewool.core.command.internal.InternalRootCommand;

import java.util.List;

public record SubCommandContext(String rootCommandAlias, String subCommandAlias, InternalRootCommand parent, List<String> args) {
}
