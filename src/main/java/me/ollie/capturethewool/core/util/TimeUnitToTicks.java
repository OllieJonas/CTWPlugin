package me.ollie.capturethewool.core.util;

import lombok.experimental.UtilityClass;

import java.util.concurrent.TimeUnit;

@UtilityClass
public class TimeUnitToTicks {

    public long calculate(long time, TimeUnit timeUnit) {
        if (timeUnit == TimeUnit.SECONDS)
            return time * 20;
        else
            return time;
    }
}
