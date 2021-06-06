package me.ollie.capturethewool.core.potion;

import org.bukkit.potion.PotionEffectType;

public class PotionEffectTypeToName {

    public static String convert(PotionEffectType type) {
        return switch (type.getName()) {
            case "ABSORPTION" -> "Absorption";
            case "BLINDNESS" -> "Blindness";
            case "CONFUSION" -> "Nausea";
            case "DAMAGE_RESISTANCE" -> "Resistance";
            case "FAST_DIGGING" -> "Haste";
            case "GLOWING" -> "Glowing";
            case "FIRE_RESISTANCE" -> "Fire Resistance";
            case "HARM" -> "Damage";
            case "HEAL" -> "Healing";
            case "INCREASE_DAMAGE", "STRENGTH" -> "Strength";
            case "INVISIBILITY" -> "Invisibility";
            case "JUMP" -> "Jump";
            case "LUCK" -> "Luck";
            case "POISON" -> "Poison";
            case "WITHER" -> "Wither";
            case "SLOW_FALLING" -> "Feather Falling";
            case "SLOW" -> "Slowness";
            case "SPEED" -> "Speed";
            case "WEAKNESS" -> "Weakness";
            default -> "Something idk what it is lmao";
        };
    }
}
