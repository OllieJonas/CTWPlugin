package me.ollie.capturethewool.core.game.team;

import me.ollie.capturethewool.core.game.AbstractGame;
import me.ollie.capturethewool.core.game.config.GameConfiguration;

import java.util.List;

public abstract class TeamGame extends AbstractGame {

    protected List<Team> teams;

    protected TeamGameConfiguration configuration;

    public TeamGame(GameConfiguration gameConfiguration, TeamGameConfiguration configuration) {
        super(gameConfiguration);
        this.configuration = configuration;
    }

    @Override
    public boolean isTeamGame() {
        return true;
    }
}
