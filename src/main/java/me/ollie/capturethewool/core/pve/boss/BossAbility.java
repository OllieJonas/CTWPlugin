package me.ollie.capturethewool.core.pve.boss;

import lombok.Setter;
import me.ollie.capturethewool.core.GamesCore;
import me.ollie.capturethewool.core.TimerTask;
import me.ollie.capturethewool.core.ability.Ability;
import me.ollie.capturethewool.core.util.CollectionUtil;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicLong;

public class BossAbility {

    @Setter
    private long start;

    private final Ability ability;

    private final long cooldownDuration;

    private final Collection<String> dialogue;

    public BossAbility(Ability ability, long cooldownDuration, Collection<String> dialogue) {
        this.ability = ability;
        this.cooldownDuration = cooldownDuration;
        this.dialogue = dialogue;
        this.start = -1;
    }

    public static BossAbility of(Ability ability, long cooldownDuration) {
        return new BossAbility(ability, cooldownDuration, Collections.emptyList());
    }

    public void call(Collection<? extends Player> players, Boss<?> boss) {
        call(players, boss.getEnemy().getEntity());
    }

    public void call(Collection<? extends Player> players, LivingEntity user) {
        Arrays.stream(CollectionUtil.random(dialogue).split("\n")).forEach(s -> players.forEach(p -> p.sendMessage(s)));
        if (ability != null) {
            ability.power(user);
        }
        long counter = GamesCore.getInstance().getCounter();

        start = GamesCore.getInstance().getCounter();
    }

    public boolean isUsable() {
        return start == -1 || TimerTask.isExpired(GamesCore.getInstance().getCounter(), start, cooldownDuration);
    }
}
