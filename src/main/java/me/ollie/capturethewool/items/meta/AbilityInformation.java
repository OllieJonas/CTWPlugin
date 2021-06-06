package me.ollie.capturethewool.items.meta;

public record AbilityInformation(String title, String description, float cooldownDuration) {

    public static AbilityInformation of(String title, String description, float cooldownDuration) {
        return new AbilityInformation(title, description, cooldownDuration);
    }

}
