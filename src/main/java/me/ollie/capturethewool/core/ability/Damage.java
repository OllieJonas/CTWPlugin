package me.ollie.capturethewool.core.ability;

import me.ollie.capturethewool.core.util.EntityUtil;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.jooq.lambda.tuple.Tuple2;

import java.util.Collection;
import java.util.List;

public interface Damage {

    Damage.Radius RADIUS = new Damage.Radius();

    Damage.Constant CONSTANT = new Damage.Constant();

    sealed interface Context permits Constant.Context, Radius.Context {}

    sealed interface Strategy<T extends Context> permits Constant, Radius {
        void inflict(T context);
    }

    final class Constant implements Strategy<Constant.Context> {

        public final record Context(LivingEntity recipient, double damage) implements Damage.Context {}

        @Override
        public void inflict(Constant.Context context) {
            context.recipient().damage(context.damage());
        }
    }

    final class Radius implements Strategy<Radius.Context> {

        public final record Context(Location origin,
                                    double radius,
                                    double maxDamage,
                                    ScalingType scaling,
                                    List<Class<? extends LivingEntity>> exclusions)
                implements Damage.Context {

            public static Context of(Location location, double radius, double maxDamage, List<Class<? extends LivingEntity>> exclusions) {
                return new Context(location, radius, maxDamage, ScalingType.CONSTANT, exclusions);
            }

            /**
             * this is supposed to be scaling based on distance (eg. someone at the edge of the radius takes less
             * damage than the person right next to it).
             *
             * I cba to expand any further than this, but seems useful so I've left the option in if required.
             */
            public enum ScalingType {
                LINEAR,
                CONSTANT
            }
        }

        @Override
        public void inflict(Context context) {
            Collection<Tuple2<LivingEntity, Double>> distances = EntityUtil.getEntitiesAround(context.origin(),
                    context.radius(),
                    context.exclusions());

            if (context.scaling() == Context.ScalingType.CONSTANT) {
                distances.forEach(t -> t.v1().damage(context.maxDamage()));

            } else if (context.scaling() == Context.ScalingType.LINEAR) {
                distances.forEach(t -> t.v1().damage(context.maxDamage() / (t.v2() + 1)));
            }
        }
    }


}
