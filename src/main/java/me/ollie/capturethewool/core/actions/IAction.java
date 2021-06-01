package me.ollie.capturethewool.core.actions;

@FunctionalInterface
public interface IAction<T extends ActionContext> {
    void execute(T context);
}
