package me.ollie.capturethewool.core.pve.boss;

import com.sun.jdi.IntegerType;
import lombok.Getter;
import me.ollie.capturethewool.core.pve.boss.phase.EndCondition;
import me.ollie.capturethewool.core.pve.boss.phase.Phase;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class PhaseList {

    private final Queue<Phase> phases;

    @Getter
    private int originalSize;

    @Getter
    private int currPhaseNo;

    @Getter
    private int currHealthPhase;

    public PhaseList() {
        this.phases = new LinkedList<>();
    }

    public PhaseList add(Phase phase) {
        phases.add(phase);
        originalSize++;
        return this;
    }

    public long sizeOfHealthBasedPhases() {
        return phases.stream().filter(p -> p.endCondition() instanceof EndCondition.HealthReduction).count();
    }

    public Phase next() {
        currPhaseNo++;
        Phase phase = phases.poll();

        if (phase != null && phase.endCondition() instanceof EndCondition.HealthReduction) {
            currHealthPhase++;
        }

        return phases.poll();
    }

    public Phase peek() {
        return phases.peek();
    }
}
