package me.ollie.capturethewool.core.projectile;

import lombok.experimental.UtilityClass;
import org.bukkit.Material;
import org.bukkit.entity.*;

@UtilityClass
public class ProjectileUtil {

    public static Material fromEntityType(EntityType type) {
        return switch (type) {
            case ARROW -> Material.ARROW;
            case SPECTRAL_ARROW -> Material.SPECTRAL_ARROW;
            case EGG -> Material.EGG;
            case SNOWBALL -> Material.SNOWBALL;
            case TRIDENT -> Material.TRIDENT;
            case THROWN_EXP_BOTTLE -> Material.EXPERIENCE_BOTTLE;
            default -> null;
        };
    }

    public static EntityType fromMaterial(Material material) {
        return switch (material) {
            case ARROW -> EntityType.ARROW;
            case SPECTRAL_ARROW -> EntityType.SPECTRAL_ARROW;
            case EGG -> EntityType.EGG;
            case SNOWBALL -> EntityType.SNOWBALL;
            case TRIDENT -> EntityType.TRIDENT;
            case EXPERIENCE_BOTTLE -> EntityType.THROWN_EXP_BOTTLE;
            default -> null;
        };
    }

    public static Class<? extends Projectile> toProjectile(EntityType type) {
        return switch (type) {
            case ARROW -> Arrow.class;
            case SPECTRAL_ARROW -> SpectralArrow.class;
            case EGG -> Egg.class;
            case SNOWBALL -> Snowball.class;
            case TRIDENT -> Trident.class;
            case THROWN_EXP_BOTTLE -> ThrownExpBottle.class;
            default -> null;
        };
    }


}
