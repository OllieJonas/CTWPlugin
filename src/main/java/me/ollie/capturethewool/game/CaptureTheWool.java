package me.ollie.capturethewool.game;

import me.ollie.capturethewool.core.game.AbstractGame;
import me.ollie.capturethewool.core.game.config.GameConfiguration;
import me.ollie.capturethewool.core.map.AbstractGameMap;
import org.bukkit.entity.Player;

import java.util.Set;

public class CaptureTheWool extends AbstractGame {

    public CaptureTheWool() {
        super(new GameConfiguration("Capture the Wool", 24, 2));
    }

    @Override
    public void load(AbstractGameMap map) {

    }

    @Override
    public void startGame(Set<Player> players) {

    }

    @Override
    public void endGame(boolean forced) {

    }

    @Override
    public boolean isTeamGame() {
        return false;
    }
}
