package me.ollie.capturethewool.items.meta;

import me.ollie.capturethewool.core.cooldown.CooldownManager;
import me.ollie.capturethewool.core.cooldown.CooldownType;
import me.ollie.capturethewool.items.types.PowerfulItem;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PowerfulItemCooldownInjector {

    private static final List<String> methodNames = new ArrayList<>();

    static {
        methodNames.add("onInteract");
        methodNames.add("onDamage");
        methodNames.add("onThrow");
        methodNames.add("onShoot");
        methodNames.add("onArrowHit");
    }

    public static void inject(PowerfulItem item) {
        boolean doesntRequireCooldown = Arrays.stream(item.getClass().getDeclaredMethods()).noneMatch(m -> methodNames.contains(m.getName()));

        if (doesntRequireCooldown) return;

        ItemStack itemStack = item.getItemStack();
        float cooldownDuration = item.getAbilityInformation().getCooldownDuration();
        String name = item.getName();

        if (cooldownDuration <= 0) return;

        System.out.println("inject " + item.cooldownType().toString() + " " + item.getName());
        CooldownManager.getInstance().register(itemStack, name, item.cooldownType(), cooldownDuration);
    }

}
