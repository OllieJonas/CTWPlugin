package me.ollie.capturethewool.items.meta;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class AbilityInformation {

    private final String title;

    private final String description;

    private final float cooldownDuration;
}
