package me.ollie.capturethewool.core.actions;

import lombok.AllArgsConstructor;
import lombok.Getter;

import me.ollie.capturethewool.core.lobby.Lobby;
import org.bukkit.entity.Player;

public class JoinLobbyAction implements IAction<JoinLobbyAction.Context> {

    @AllArgsConstructor
    @Getter
    public static class Context implements ActionContext {

        private final Player player;

        private final Lobby lobby;

    }

    @Override
    public void execute(Context context) {
        context.getLobby().addPlayer(context.getPlayer());
    }
}
