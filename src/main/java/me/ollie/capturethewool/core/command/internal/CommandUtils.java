package me.ollie.capturethewool.core.command.internal;

import lombok.SneakyThrows;
import org.bukkit.command.CommandSender;

import java.lang.annotation.Annotation;
import java.util.Optional;

public class CommandUtils {

    public record InternalCommandInfo(String usage, String shortDescription, String longDescription) {}

    public static final boolean DEFAULT_REQUIRES_OP_VALUE = false;

    public static final String DEFAULT_PERMISSION_VALUE = "";

    public static final InternalCommandInfo DEFAULT_COMMAND_INFO = new InternalCommandInfo("", "", "");

    public static <T extends Annotation> Optional<T> getAnnotation(Class<?> clazz, Class<T> annotation) {
        if (!clazz.isAnnotationPresent(annotation)) return Optional.empty();

        return Optional.of(clazz.getDeclaredAnnotation(annotation));
    }

    @SneakyThrows
    public static <T extends ICommand> T newInstance(Class<T> clazz) {
        return clazz.getConstructor().newInstance();
    }

    public static boolean hasPermission(CommandSender sender, String permission, boolean requiresOp) {
        return sender.isOp() ||
                ((!sender.isOp() && !requiresOp) &&
                        (permission.equals("") || sender.hasPermission(permission)));
    }
}
