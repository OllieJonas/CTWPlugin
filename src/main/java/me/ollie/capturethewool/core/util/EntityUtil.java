package me.ollie.capturethewool.core.util;

import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.jooq.lambda.Seq;
import org.jooq.lambda.tuple.Tuple2;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EntityUtil {

//    public static Sound getSoundFor(LivingEntity damagee)  {
//
//            return switch(damagee.getType()) {
//                case EntityType.BAT -> Sound.ENTITY_BAT_HURT;
//                case EntityType.BLAZE -> Sound.ENTITY_BLAZE_HURT;
//                case EntityType.CAVE_SPIDER -> Sound.ENTITY_SPIDER_HURT;
//                case EntityType.CHICKEN -> Sound.ENTITY_CHICKEN_HURT;
//                case EntityType.COW -> Sound.ENTITY_COW_HURT;
//                case EntityType.CREEPER -> Sound.ENTITY_CREEPER_HURT;
//                case EntityType.ENDER_DRAGON -> Sound.ENTITY_ENDER_DRAGON_HURT;
//                case EntityType.ENDERMAN -> Sound.ENTITY_ENDERMAN_HURT;
//                case EntityType.GHAST -> Sound.ENTITY_GHAST_HURT;
//                case EntityType.GIANT -> Sound.ENTITY_ZOMBIE_HURT;
//                case EntityType.HORSE -> Sound.ENTITY_HORSE_HURT;
//                case EntityType.IRON_GOLEM -> Sound.ENTITY_IRON_GOLEM_HURT;
//                case EntityType.MAGMA_CUBE -> Sound.ENTITY_MAGMA_CUBE_HURT;
//                case EntityType.MUSHROOM_COW -> Sound.ENTITY_COW_HURT;
//                case EntityType.OCELOT -> Sound.ENTITY_OCELOT_HURT;
//                case EntityType.PIG -> Sound.ENTITY_PIG_HURT;
//                case EntityType.PIGLIN -> Sound.ENTITY_PIGLIN_HURT;
//                case EntityType.SHEEP -> Sound.ENTITY_SHEEP_HURT;
//                case EntityType.SILVERFISH -> Sound.ENTITY_SILVERFISH_HURT;
//                case EntityType.SKELETON -> Sound.ENTITY_SKELETON_HURT;
//                case EntityType.SLIME -> Sound.ENTITY_SLIME_HURT;
//                case EntityType.SNOWMAN -> Sound.ENTITY_SNOW_GOLEM_HURT;
//                case EntityType.SPIDER -> Sound.ENTITY_SPIDER_HURT;
//                case EntityType.SQUID -> Sound.ENTITY_SQUID_HURT;
//                case EntityType.VILLAGER -> Sound.ENTITY_VILLAGER_HURT;
//                case EntityType.WITCH -> Sound.ENTITY_WITCH_HURT;
//                case EntityType.WITHER -> Sound.ENTITY_WITHER_HURT;
//                case EntityType.WOLF -> Sound.ENTITY_WOLF_HURT;
//                case EntityType.ZOMBIE -> Sound.ENTITY_ZOMBIE_HURT;
//                default -> Sound.ENTITY_ZOMBIE_HURT;
//            };
//        }

    public static Optional<Tuple2<LivingEntity, Double>> getClosest(LivingEntity entity) {
        return getClosest(entity, 64, 64, 64);
    }

    public static Optional<Tuple2<LivingEntity, Double>> getClosest(LivingEntity entity, double x, double y, double z) {
        Stream<LivingEntity> livingEntities = entity.getNearbyEntities(x, y, z).stream()
                .filter(e -> e instanceof LivingEntity)
                .map(e -> (LivingEntity) e);
        return Seq.zip(livingEntities,
                livingEntities
                .map(e -> LocationUtil.distanceSquared(e.getLocation(), entity.getLocation())))
                .min(Comparator.comparingDouble(Tuple2::v2));
    }

    public static <T extends LivingEntity> Optional<Tuple2<T, Double>> getClosest(LivingEntity entity, Class<T> clazz) {
        return getClosest(entity, clazz, 64, 64, 64);
    }

    public static <T extends LivingEntity> Optional<Tuple2<T, Double>> getClosest(LivingEntity entity, Class<T> clazz, double x, double y, double z) {
        Stream<T> livingEntities = entity.getNearbyEntities(x, y, z).stream()
                .filter(e -> e instanceof LivingEntity)
                .map(e -> (LivingEntity) e)
                .filter(e -> e.getClass().isAssignableFrom(clazz))
                .map(clazz::cast);
        return Seq.zip(livingEntities, livingEntities.map(e -> LocationUtil.distanceSquared(e.getLocation(), entity.getLocation()))).min(Comparator.comparingDouble(Tuple2::v2));
    }
}
