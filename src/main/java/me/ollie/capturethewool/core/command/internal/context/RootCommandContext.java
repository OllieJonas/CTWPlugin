package me.ollie.capturethewool.core.command.internal.context;

import java.util.List;

public record RootCommandContext(String alias, List<String> args) {
}
