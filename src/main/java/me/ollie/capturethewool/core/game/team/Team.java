package me.ollie.capturethewool.core.game.team;

import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

@Getter
public class Team {

    private final TeamColour colour;

    private final Set<Player> players;

    public Team(TeamColour colour) {
        this.colour = colour;
        this.players = new HashSet<>();
    }

}
