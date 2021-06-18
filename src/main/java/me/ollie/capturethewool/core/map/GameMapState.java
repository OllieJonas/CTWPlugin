package me.ollie.capturethewool.core.map;

public class GameMapState {

    private final AbstractGameMap map;

    private State state;

    public GameMapState(AbstractGameMap map, State state) {
        this.map = map;
        this.state = state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public AbstractGameMap getMap() {
        return map;
    }

    public State getState() {
        return state;
    }

    enum State {
        LOADING,
        WAITING,
        STARTING,
        IN_GAME,
        ENDED,
        FINISHING;
    }
}
