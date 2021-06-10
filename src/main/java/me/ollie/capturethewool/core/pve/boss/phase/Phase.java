package me.ollie.capturethewool.core.pve.boss.phase;

import me.ollie.capturethewool.core.ability.Ability;
import me.ollie.capturethewool.core.pve.boss.AbilityTriggerReason;
import me.ollie.capturethewool.core.pve.boss.BossAbility;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface Phase {

    void onStart();

    Map<AbilityTriggerReason, BossAbility> abilitySet();

    EndCondition endCondition();

    void onFinish();
}
