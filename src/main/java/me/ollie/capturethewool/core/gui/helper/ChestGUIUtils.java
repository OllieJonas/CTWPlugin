package me.ollie.capturethewool.core.gui.helper;

import lombok.experimental.UtilityClass;
import me.ollie.capturethewool.core.util.SetUtils;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@UtilityClass
public class ChestGUIUtils {

    private void test() {
        System.out.println("Inventory Size: " + IntStream.range(0, 40)// Test Inventory Size based on number of slots
                .map(ChestGUIUtils::calculateInventorySize)
                .boxed()
                .map(String::valueOf)
                .collect(Collectors.joining(", ")));

        System.out.println("Inv Size to Slots Required: " + IntStream.of(27, 36, 45) // Tests Inventory Size to maximum number of slots required
                .map(ChestGUIUtils::invSizeToSlotsRequired)
                .boxed()
                .map(String::valueOf)
                .collect(Collectors.joining(", ")));

        System.out.println("Calculating Filler: " + IntStream.of(27, 36, 45) // Tests calculating the filler
                .boxed()
                .map(ChestGUIUtils::calculateFiller)
                .map(String::valueOf)
                .collect(Collectors.joining("\n")));

        System.out.println("Inventory Slots: " + IntStream.rangeClosed(0, 33)
                .map(ChestGUIUtils::shiftToBorderPosition)
                .boxed()
                .map(String::valueOf)
                .collect(Collectors.joining(", ")));
    }

    /**
     * Calculates the locations of filler based on the maximum amount of slots calculated by invSizeToSlotsRequired.
     *
     * @param invSize Size of the integers
     * @return Location of filler.
     */
    public Set<Integer> calculateFiller(int invSize) {
        return SetUtils.difference(
                IntStream.range(0, invSize)
                        .boxed()
                        .collect(Collectors.toSet()),
                IntStream.range(0, invSizeToSlotsRequired(invSize))
                        .map(ChestGUIUtils::shiftToBorderPosition)
                        .boxed()
                        .collect(Collectors.toSet()));
    }

    /**
     * Shifts a position to allow for edges on a Bukkit menu.
     * <p>
     * Examples: 0 -> 10, 5 -> 15, 8 -> 19, 9 -> 20
     *
     * @param original Original value
     * @return Shifted value
     */
    public int shiftToBorderPosition(int original) {
        return 2 * (original / 7) + original + 10;
    }

    /**
     * Calculates the inventory size to use for Bukkit.createInventory() based on the maximum slots
     * required. Note this is capped at the maximum size of 54.
     * <p>
     * Examples: 4 -> 27, 8 -> 36, 17 -> 45
     *
     * @param slotsRequired The number of slots required
     * @return The appropriate size of inventory
     */
    public int calculateInventorySize(int slotsRequired) {
        return Math.min(54, (3 + (slotsRequired / 7)) * 9);
    }

    public int calculateInventorySizeWithSpace(int slotsRequired) {
        return Math.min(54, calculateInventorySize(slotsRequired) + 18);
    }

    /**
     * Calculates the maximum number of slots required based on the inventory size.
     * <p>
     * Examples: 27 -> 7, 36 -> 14, 45 -> 21
     *
     * @param invSize The size of the inventory
     * @return The maximum amount of slots required
     */
    public int invSizeToSlotsRequired(int invSize) {
        return 7 * ((invSize / 9) - 2);
    }
}