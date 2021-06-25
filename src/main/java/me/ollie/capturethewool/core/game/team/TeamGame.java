package me.ollie.capturethewool.core.game.team;

import me.ollie.capturethewool.core.game.AbstractGame;
import me.ollie.capturethewool.core.game.config.GameConfiguration;

import java.util.List;
import java.util.stream.Collectors;

public abstract class TeamGame extends AbstractGame {

    protected List<Team> teams;

    protected TeamGameConfiguration configuration;

    public TeamGame(GameConfiguration gameConfiguration, TeamGameConfiguration configuration) {
        super(gameConfiguration);
        this.configuration = configuration;
        this.teams = initTeams(configuration.noTeams());
    }

    private List<Team> initTeams(int noTeams) {
        return TeamColour.getFirstNTeams(noTeams).stream().map(Team::new).toList();
    }

    @Override
    public boolean isTeamGame() {
        return true;
    }
}
