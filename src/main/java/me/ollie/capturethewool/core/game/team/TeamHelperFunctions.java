package me.ollie.capturethewool.core.game.team;

import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class TeamHelperFunctions {

    public static List<Team> generateTeams(int noTeams) {
        return TeamColour.getFirstNTeams(noTeams).stream().map(Team::new).collect(Collectors.toList());
    }

}
