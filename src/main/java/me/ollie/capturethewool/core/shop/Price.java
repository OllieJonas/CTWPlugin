package me.ollie.capturethewool.core.shop;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.ollie.capturethewool.core.util.ItemStackUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.stream.Collectors;

public class Price {

    private static final String TICK = "✓";
    private static final String CROSS = "✖";

    @Getter
    private final LinkedHashMap<Currency, Integer> currencyAmounts;

    public Price(LinkedHashMap<Currency, Integer> currencyAmounts) {
        this.currencyAmounts = currencyAmounts;
    }

    public Price(Item... amounts) {
        this.currencyAmounts = new LinkedHashMap<>();
        Arrays.stream(amounts).distinct().forEach(i -> currencyAmounts.put(i.getCurrency(), i.getAmount()));
    }

    public boolean hasRequirements(Player player) {
        return requirementStatistics(player).stream().map(Requirement::hasEnough).allMatch(e -> e.equals(true));
    }

    public boolean pay(Player player) {
        if (!hasRequirements(player)) return false;

        ItemStack[] itemsToRemove = currencyAmounts.entrySet().stream()
                .map(c -> c.getKey().itemRepresentation().asQuantity(c.getValue()))
                .toArray(ItemStack[]::new);

        player.getInventory().removeItem(itemsToRemove);
        player.updateInventory();
        return true;
    }

    @AllArgsConstructor(staticName = "of")
    @Getter
    public static class Item {
        private final Currency currency;

        private final int amount;
    }

    public List<String> itemLore(Player player) {
        List<String> lore = new ArrayList<>();

        List<Requirement> requirementStatistics = requirementStatistics(player);

        List<Map.Entry<Currency, Integer>> currencies = new ArrayList<>(currencyAmounts.entrySet());

        boolean meetsRequirements = hasRequirements(player);

        String prompt = meetsRequirements ? ChatColor.GREEN + "Click to purchase!" : ChatColor.RED + "You cannot afford this!";
        lore.add(ChatColor.GRAY + "Price: ");

        for (int i = 0; i < requirementStatistics.size(); i++) {
            Map.Entry<Currency, Integer> currency = currencies.get(i);
            Requirement requirement = requirementStatistics.get(i);
            String toAdd = ChatColor.DARK_GRAY + " - " + currency.getKey().formattedName() + " " + requirement.getLore();
            lore.add(toAdd);
        }
        lore.add(" ");
        lore.add(prompt);
        return lore;
    }

    public List<Requirement> requirementStatistics(Player player) {
        return currencyAmounts.entrySet().stream()
                .map(e -> new Requirement(Arrays.stream(player.getInventory().getContents())
                        .filter(i -> ItemStackUtil.weakEquals(i, e.getKey().itemRepresentation()))
                        .filter(Objects::nonNull)
                        .mapToInt(ItemStack::getAmount).sum(), e.getValue()))
                .collect(Collectors.toList());
    }

    private record Requirement(int amountHave, int amountRequired) {

        public boolean hasEnough() {
            return amountHave >= amountRequired;
        }

        public String getLore() {
            boolean hasEnough = hasEnough();
            return ChatColor.DARK_GRAY + "" + (hasEnough ? ChatColor.GREEN : ChatColor.RED) + "" + amountHave + ChatColor.DARK_GRAY + "/" +
                    ChatColor.GRAY + amountRequired + ChatColor.DARK_GRAY + "  " + (hasEnough ? ChatColor.GREEN + TICK : ChatColor.RED + CROSS);
        }
    }
}
