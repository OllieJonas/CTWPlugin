package me.ollie.capturethewool.core.gui;

import me.ollie.capturethewool.core.gui.helper.ChestGUIUtils;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;

public class GUIEvents implements Listener {

    private final GUIManager manager;

    public GUIEvents(GUIManager manager) {
        this.manager = manager;
    }

    @EventHandler
    public void onInteract(InventoryClickEvent event) {

        HumanEntity entity = event.getWhoClicked();
        if (entity instanceof Player player) {
            GUI gui = manager.getGuiFor(player);

            if (gui == null)
                return;

            event.setCancelled(true);

            int slot = event.getSlot();
            GUIItem item = gui.getItem(slot);

            if (item == null)
                return;

            if (event.getInventory().getType() != InventoryType.CHEST) // weird thing in paper where it considers your hotbar n stuff as well
                return;

            item.action().accept(player, gui.isHasBorder() ? ChestGUIUtils.shiftFromBorderPosition(slot) : slot, event.getCurrentItem());

            if (item.itemClosesMenu())
                manager.closeGuiFor(player);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onClose(InventoryCloseEvent event) {
        if (event.getInventory().getType() != InventoryType.CHEST)
            return;

        HumanEntity entity = event.getPlayer();

        if (entity instanceof Player player) {

            manager.closeGuiFor(player);
        }
    }
}
