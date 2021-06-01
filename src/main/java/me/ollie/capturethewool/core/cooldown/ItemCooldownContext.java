package me.ollie.capturethewool.core.cooldown;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class ItemCooldownContext {

    private final String label;

    private final float duration;

    private final CooldownType type;

    private final boolean usesProgressBar;
}
