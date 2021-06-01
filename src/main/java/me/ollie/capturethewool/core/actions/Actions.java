package me.ollie.capturethewool.core.actions;

public class Actions {

    sealed interface IAction<T extends Contexts> permits JoinLobbyAction, LeaveLobbyAction {
        void execute(T context);
    }

    public static final class JoinLobbyAction implements IAction<Contexts.Join> {

        @Override
        public void execute(Contexts.Join context) {

        }
    }

    public static final class LeaveLobbyAction implements IAction<Contexts.Leave> {

        @Override
        public void execute(Contexts.Leave context) {

        }
    }


}
