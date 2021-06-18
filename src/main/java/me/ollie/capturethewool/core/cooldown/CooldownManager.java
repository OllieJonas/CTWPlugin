package me.ollie.capturethewool.core.cooldown;

import lombok.Getter;
import me.ollie.capturethewool.core.GamesCore;
import me.ollie.capturethewool.core.cooldown.progress.ItemProgressBarEvent;
import me.ollie.capturethewool.core.cooldown.progress.ProgressBar;
import me.ollie.capturethewool.core.cooldown.progress.ProgressBarManager;
import me.ollie.capturethewool.core.TimerTask;
import me.ollie.capturethewool.core.util.ReducedItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.stream.Collectors;

@Getter
public class CooldownManager {

    @Getter
    private static CooldownManager instance;

    private final JavaPlugin plugin;

    private final Map<Player, Map<CooldownType, List<CooldownState>>> states;

    private final Map<ReducedItemStack, ItemCooldownContext> itemCooldownLabels;

    public CooldownManager(GamesCore core) {
        this.plugin = core.getPlugin();
        this.states = new HashMap<>();
        this.itemCooldownLabels = new HashMap<>();

        plugin.getServer().getPluginManager().registerEvents(new CooldownEvents(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new ItemProgressBarEvent(), plugin);

        instance = this;
    }

    public boolean hasActiveCooldowns(Player player) {
        return states.get(player) != null && !states.get(player).isEmpty();
    }

    public boolean itemIsInCooldown(Player player, ItemStack item) {
        ReducedItemStack reduced = ReducedItemStack.from(item);
        ItemCooldownContext context = itemCooldownLabels.get(reduced);

        if (context == null) return false;

        Map<CooldownType, List<CooldownState>> typeListMap = states.get(player);

        if (typeListMap == null) return false;

        List<CooldownState> sts = typeListMap.get(context.type());

         System.out.println(states.get(player).entrySet().stream().map(e -> e.getKey().toString() + ": " + e.getValue().stream().map(s -> s.label().name()).collect(Collectors.joining(", "))).collect(Collectors.joining("\n")));

        if (sts == null || sts.isEmpty()) return false;

        for (CooldownState state : sts) {

            boolean equals = state.label().equals(reduced);
            boolean isExpired = TimerTask.isExpired(getCounter(), state.startTime(), context.duration());

            if (equals) {
                if (isExpired) {
                    sts.remove(state);
                    states.get(player).put(context.type(), sts);
                    return false;
                }
                return true;
            }
        }
        return false;
    }

    public long getCounter() {
        return GamesCore.getInstance().getCounter();
    }

    public void register(ItemStack item, String label, CooldownType type, float duration) {
        register(item, new ItemCooldownContext(label, duration, type, true));
    }

    public boolean isRegistered(ItemStack item) {
        return itemCooldownLabels.containsKey(ReducedItemStack.from(item));
    }

    public void register(ItemStack item, ItemCooldownContext context) {
        itemCooldownLabels.put(ReducedItemStack.from(item), context);
    }

    public void add(Player player, CooldownType type, ItemStack item) {
        ItemCooldownContext context = itemCooldownLabels.get(ReducedItemStack.from(item));

        if (context == null) return;

        if (!states.containsKey(player)) states.put(player, new HashMap<>());

        if (!states.get(player).containsKey(type)) states.get(player).put(type, new ArrayList<>());

        CooldownState cooldownState = new CooldownState(ReducedItemStack.from(item), getCounter());

        if (!states.get(player).get(type).contains(cooldownState))
            states.get(player).get(type).add(cooldownState);

        if (context.usesProgressBar()) {
            ProgressBarManager.getInstance().addAndShow(player, new ProgressBar(plugin, player, context.label(), context.duration()));
        }
    }
}
