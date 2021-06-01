package me.ollie.capturethewool.core.game.team;

import lombok.AllArgsConstructor;

@AllArgsConstructor(staticName = "of")
public class TeamGameConfiguration {

    private int noTeams;

    private int maxTeamSize;

    private boolean isFriendlyFireEnabled;
}
