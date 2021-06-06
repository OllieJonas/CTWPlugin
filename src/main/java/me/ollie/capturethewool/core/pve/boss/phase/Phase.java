package me.ollie.capturethewool.core.pve.boss.phase;

import me.ollie.capturethewool.core.ability.Ability;

import java.util.Collection;
import java.util.List;

public interface Phase {

    void onStart();

    Collection<Ability> abilitySet();

    boolean endCondition();

    void onFinish();
}
