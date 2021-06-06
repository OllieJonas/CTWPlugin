package me.ollie.capturethewool.core.pve.boss;

import me.ollie.capturethewool.core.pve.boss.phase.Phase;

import java.util.LinkedList;
import java.util.Queue;

public class PhaseList {

    private final Queue<Phase> phases;

    public PhaseList() {
        this.phases = new LinkedList<>();
    }

    public Phase next() {
        return phases.poll();
    }
}
