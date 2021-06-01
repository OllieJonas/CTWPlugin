package me.ollie.capturethewool.core.cooldown;

import lombok.Getter;

// this is stupidly unsafe but honestly its just cooldowns who really cares
// only did this bc System.currentTimesMillis() didnt work big sad
public class TimerTask implements Runnable {

    @Getter
    private long counter;

    @Override
    public void run() {
        counter++;
    }

    public static float timeElapsed(long counter, long start) {
        return (float) (counter - start) / 10;
    }

    public static boolean isExpired(long counter, long start, float duration) {
        return timeElapsed(counter, start) >= duration;
    }
}
