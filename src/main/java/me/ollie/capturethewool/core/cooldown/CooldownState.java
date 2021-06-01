package me.ollie.capturethewool.core.cooldown;

import java.util.Objects;

public record CooldownState(ReducedItemStack label, long startTime) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CooldownState state = (CooldownState) o;
        return label.equals(state.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label);
    }
}
