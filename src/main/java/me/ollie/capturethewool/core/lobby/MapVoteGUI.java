package me.ollie.capturethewool.core.lobby;

import lombok.Getter;
import me.ollie.capturethewool.core.gui.GUI;
import me.ollie.capturethewool.core.gui.GUIItem;
import me.ollie.capturethewool.core.gui.GUIManager;
import me.ollie.capturethewool.core.map.AbstractGameMap;
import me.ollie.capturethewool.core.util.ItemStackBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.stream.IntStream;

public class MapVoteGUI {

    private static final BiFunction<AbstractGameMap, Integer, ItemStack> mapToItemFunction = (map, votes) -> new ItemStackBuilder(Material.PAPER)
            .withName(ChatColor.DARK_AQUA + map.getName())
            .withLore(ChatColor.GRAY + "Click to vote!", " ", ChatColor.GRAY + "Votes: " + ChatColor.AQUA + votes)
            .build();

    private static final ItemStack VOTE = new ItemStackBuilder(Material.LIME_STAINED_GLASS_PANE).withName(ChatColor.BLACK + "|").build();

    private static final ItemStack NON_VOTE = new ItemStackBuilder(Material.RED_STAINED_GLASS_PANE).withName(ChatColor.BLACK + "|").build();

    @Getter
    private GUI gui;

    public MapVoteGUI(MapVote mapVote) {
        initGui(mapVote);
    }

    private void initGui(MapVote mapVote) {
        this.gui = new GUI("Map Voting - Press ESC to Exit!", 54) {
            @Override
            public void addItems() {
                AtomicInteger counter = new AtomicInteger(0);
                mapVote.getTalley()
                        .forEach((map, talley) -> {
                            GUIItem item = GUIItem.of(
                                    mapToItemFunction.apply(map, talley),
                                    (p, i) -> {
                                        mapVote.vote(p, map);
                                        // GUIManager.getInstance().notifyAllObservers(this);
                                    },
                                    false);

                            int count = counter.get();
                            add(count, item);
                            IntStream.rangeClosed(1, Math.min(talley, 6)).forEach(i -> add(i + count, GUIItem.empty(VOTE)));

                            if (talley + 1 < 6)
                                IntStream.rangeClosed(talley + 1, 6).forEach(i -> add(i + count, GUIItem.empty(NON_VOTE)));


                            counter.getAndAdd(7);
                        });
            }
        };
    }
}
