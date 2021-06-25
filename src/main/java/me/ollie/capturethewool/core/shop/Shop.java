package me.ollie.capturethewool.core.shop;

import me.ollie.capturethewool.core.gui.GUI;
import me.ollie.capturethewool.core.gui.GUIItem;
import me.ollie.capturethewool.core.gui.helper.ChestGUIUtils;
import me.ollie.capturethewool.core.util.ItemStackUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Shop extends GUI {

    private final Map<ItemStack, Commodity> commodities;

    public Shop(Player player, String title, Collection<Commodity> commodities) {
        super(player, title, ChestGUIUtils.calculateInventorySize(commodities.size()));

        this.commodities = new LinkedHashMap<>();

        commodities.forEach(c -> this.commodities.put(ItemStackUtil.stripLore(c.item().clone()), c));
    }

    @Override
    public void addItems() {
        AtomicInteger count = new AtomicInteger(0);

        commodities.forEach((i, c) -> add(count.getAndIncrement(), GUIItem.of(c.buildIcon(player), (pl, item) -> {
            Commodity commodity = commodities.get(ItemStackUtil.stripLore(item.clone()));

            if (commodity == null) {
                pl.sendMessage(ChatColor.RED + "Commodity doesnt exist (shouldn't see this)");
                return;
            }

            boolean payed = c.price().pay(pl);

            String toSend = payed ? ChatColor.GREEN + "Successfully bought " + item.getItemMeta().getDisplayName() : ChatColor.RED + "You cannot afford this! :(";

            if (payed)
                pl.getInventory().addItem(commodity.item());

            pl.sendMessage(toSend);

            redraw();

        }, false)));
    }
}
