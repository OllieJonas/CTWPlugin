package me.ollie.capturethewool.core.command.annotations;

import me.ollie.capturethewool.core.command.ICommand;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SubCommand {

    Class<? extends ICommand>[] root();

    String name();
}
