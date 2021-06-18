package me.ollie.capturethewool.core.shop;

import me.ollie.capturethewool.core.GamesCore;
import me.ollie.capturethewool.core.gui.GUI;
import me.ollie.capturethewool.core.gui.GUIItem;
import me.ollie.capturethewool.core.gui.helper.ChestGUIUtils;
import me.ollie.capturethewool.core.util.ItemStackUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;

public class Shop extends GUI {

    private final Map<ItemStack, Commodity> commodities;

    private final List<Commodity> commoditiesList;

    public Shop(Player player, String title, List<Commodity> commodities) {
        super(player, title, ChestGUIUtils.calculateInventorySize(commodities.size()));

        this.commodities = new LinkedHashMap<>();
        this.commoditiesList = commodities;

        // strip lore to avoid wackiness when getting the commodity on click after the additional lore is added
        // when doing price requirements
    }

    @Override
    public void addItems() {
        commoditiesList.forEach(c -> commodities.put(ItemStackUtil.stripLore(c.getItem().clone()), c));

        AtomicInteger count = new AtomicInteger(0);
        commodities.forEach((i, c) -> add(count.getAndIncrement(), GUIItem.of(c.buildIcon(player), (pl, item) -> {
            Commodity commodity = commodities.get(ItemStackUtil.stripLore(item));

            if (commodity == null) {
                pl.sendMessage(ChatColor.RED + "Commodity doesnt exist (shouldnt see this)");
                return;
            }

            boolean payed = c.getPrice().pay(pl);

            String toSend = payed ? ChatColor.GREEN + "Successfully bought " + item.getItemMeta().getDisplayName() : ChatColor.RED + "You cannot afford this! :(";

            if (payed)
                pl.getInventory().addItem(commodity.getItem());

            pl.sendMessage(toSend);

            redraw();

        }, false)));
    }
}
