package me.ollie.capturethewool.core.pve.boss.events;

import lombok.Getter;
import me.ollie.capturethewool.core.pve.boss.Boss;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public abstract class BossEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    @Getter
    protected final Boss<?> boss;

    public BossEvent(Boss<?> boss) {
        this.boss = boss;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
