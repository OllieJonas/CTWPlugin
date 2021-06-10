package me.ollie.capturethewool.boss.pirateboss;

import me.ollie.capturethewool.core.pve.boss.AbilityTriggerReason;
import me.ollie.capturethewool.core.pve.boss.BossAbility;
import me.ollie.capturethewool.core.pve.boss.phase.EndCondition;
import me.ollie.capturethewool.core.pve.boss.phase.Phase;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.List;
import java.util.Map;

public class CaptainAbilityPhase implements Phase {

    private static final World COAST = Bukkit.getWorld("coast");

    public static final List<Location> CANNON_LOCATIONS = List.of(
            new Location(COAST, 1279.5, 128, 1587.5),
            new Location(COAST, 1275.5, 127, 1587.5),
            new Location(COAST, 1271.5, 127, 1586.5),
            new Location(COAST, 1267.5, 127, 1585.5),
            new Location(COAST, 1263.5, 127, 1585.5),
            new Location(COAST, 1259.5, 127, 1585.5),
            new Location(COAST, 1255.5, 127, 1585.5),
            new Location(COAST, 1251.5, 127, 1586.5)
    );

    @Override
    public void onStart() {

    }

    @Override
    public Map<AbilityTriggerReason, BossAbility> abilitySet() {
        return Map.of(AbilityTriggerReason.RANDOM_DURATION, new BossAbility(new CannonFireAbility(CANNON_LOCATIONS), 1000L, List.of("Rain fire! \n But sire, our troops! \nJust do it!")));
    }

    @Override
    public EndCondition endCondition() {
        return new EndCondition.HealthReduction();
    }

    @Override
    public void onFinish() {
        Bukkit.getOnlinePlayers().forEach(p -> p.sendMessage("captain ability has finished"));
    }
}
