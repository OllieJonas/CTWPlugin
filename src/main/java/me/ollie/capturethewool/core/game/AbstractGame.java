package me.ollie.capturethewool.core.game;

import lombok.Getter;
import lombok.Setter;
import me.ollie.capturethewool.core.game.config.GameConfiguration;
import me.ollie.capturethewool.core.game.kit.Kit;
import me.ollie.capturethewool.core.map.AbstractGameMap;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
public abstract class AbstractGame {

    protected final GameConfiguration configuration;

    protected final Set<Player> spectators;

    protected final List<Kit> kits;

    protected final List<AbstractGameMap> maps;

    @Setter
    protected Set<Player> players;

    public AbstractGame(GameConfiguration configuration) {
        this.configuration = configuration;
        this.players = new HashSet<>();
        this.kits = new ArrayList<>();
        this.maps = new ArrayList<>();
        this.spectators = new HashSet<>();
    }

    public abstract void load(AbstractGameMap map);

    public abstract void startGame(Set<Player> players);

    public abstract void endGame(boolean forced);

    public void endGame() {
        endGame(false);
    }

    public abstract boolean isTeamGame();

    public boolean isKitGame() {
        return !kits.isEmpty();
    }
}
