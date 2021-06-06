package me.ollie.capturethewool.core.pve.boss;

import me.ollie.capturethewool.core.ability.Ability;
import me.ollie.capturethewool.core.pve.boss.phase.Phase;

import java.util.Collection;

public class InvinciblePhase implements Phase {

    @Override
    public void onStart() {

    }

    @Override
    public Collection<Ability> abilitySet() {
        return null;
    }

    @Override
    public boolean endCondition() {
        return false;
    }

    @Override
    public void onFinish() {

    }
}
