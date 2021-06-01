package me.ollie.capturethewool.core.cooldown;

public record ItemCooldownContext(String label, float duration, CooldownType type, boolean usesProgressBar) {}
