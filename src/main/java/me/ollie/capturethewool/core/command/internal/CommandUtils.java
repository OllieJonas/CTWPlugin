package me.ollie.capturethewool.core.command.internal;

import lombok.SneakyThrows;
import me.ollie.capturethewool.core.command.ICommand;

import java.lang.annotation.Annotation;
import java.util.Optional;

class CommandUtils {

    public record InternalCommandInfo(String usage, String shortDescription, String longDescription) {}

    public static final boolean DEFAULT_REQUIRES_OP_VALUE = false;

    public static final String DEFAULT_PERMISSION_VALUE = "";

    public static final InternalCommandInfo DEFAULT_COMMAND_INFO = new InternalCommandInfo("", "", "");

    public static <T extends Annotation> Optional<T> getAnnotation(Class<?> clazz, Class<T> annotation) {
        if (!clazz.isAnnotationPresent(annotation)) return Optional.empty();
        return Optional.of(clazz.getAnnotation(annotation));
    }

    @SneakyThrows
    public static <T extends ICommand> T newInstance(Class<T> clazz) {
        return clazz.getConstructor().newInstance();
    }
}
