package me.ollie.capturethewool.core.command.meta.internal.context;

import me.ollie.capturethewool.core.command.meta.internal.InternalRootCommand;

import java.util.List;

public record SubCommandContext(String rootCommandAlias, String subCommandAlias, InternalRootCommand parent, List<String> args) {
}
