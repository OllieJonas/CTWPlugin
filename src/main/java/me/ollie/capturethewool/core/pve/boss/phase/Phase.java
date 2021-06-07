package me.ollie.capturethewool.core.pve.boss.phase;

import me.ollie.capturethewool.core.ability.Ability;
import me.ollie.capturethewool.core.pve.boss.AbilityTriggerReason;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface Phase {

    void onStart();

    Map<AbilityTriggerReason, Ability> abilitySet();

    EndCondition endCondition();

    void onFinish();
}
