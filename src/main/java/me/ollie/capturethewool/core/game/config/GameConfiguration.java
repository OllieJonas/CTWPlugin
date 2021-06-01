package me.ollie.capturethewool.core.game.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GameConfiguration {

    private final String name;

    private final int maxPlayers;

    private final int minPlayersToStart;
}
