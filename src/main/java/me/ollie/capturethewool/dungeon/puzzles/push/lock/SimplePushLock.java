package me.ollie.capturethewool.dungeon.puzzles.push.lock;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SimplePushLock implements PushLock {
    @Getter
    private boolean locked;

    public void lock() {
        this.locked = true;
    }

    public void unlock() {
        this.locked = false;
    }
}
