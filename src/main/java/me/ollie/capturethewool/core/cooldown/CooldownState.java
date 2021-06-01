package me.ollie.capturethewool.core.cooldown;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@Getter
@AllArgsConstructor(staticName = "of")
public class CooldownState {

    private final ReducedItemStack label;

    private final long startTime;

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
