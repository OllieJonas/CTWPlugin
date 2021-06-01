package me.ollie.capturethewool.core.hologram;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.gmail.filoghost.holographicdisplays.api.line.ItemLine;
import com.gmail.filoghost.holographicdisplays.api.line.TextLine;
import com.gmail.filoghost.holographicdisplays.api.line.TouchableLine;
import me.ollie.capturethewool.core.util.control.Either;
import me.ollie.capturethewool.core.util.control.OptionalPair;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class HologramBuilder {

    @FunctionalInterface
    public interface OnTouch {
        void onTouch(Player player, TouchableLine line);
    }

    @FunctionalInterface
    public interface OnPickup {
        void onPickup(Player player, ItemLine line);
    }

    private final JavaPlugin plugin;

    private final Location location;

    private final Map<Either<String, ItemStack>, OptionalPair<OnTouch, OnPickup>> lines;

    private OnTouch onTouchDefault;

    private OnPickup onPickupDefault;

    private OnTouch onTouchOverride;

    private OnPickup onPickupOverride;

    public HologramBuilder(JavaPlugin plugin, Location location) {
        this.plugin = plugin;
        this.location = location;
        this.lines = new LinkedHashMap<>();
    }

    // ---------- OVERRIDE ON TOUCH ----------

    public HologramBuilder onTouchOverride(OnTouch onTouch) {
        this.onTouchOverride = onTouch;
        return this;
    }

    public HologramBuilder onPickupOverride(OnPickup onPickup) {
        this.onPickupOverride = onPickup;
        return this;
    }

    // ---------- DEFAULT ON TOUCH ----------

    public HologramBuilder onTouchDefault(OnTouch onTouch) {
        this.onTouchDefault = onTouch;
        return this;
    }

    public HologramBuilder onPickupDefault(OnPickup onPickup) {
        this.onPickupDefault = onPickup;
        return this;
    }

    // ---------- LINE NO TOUCH ----------
    public HologramBuilder line(String line) {
        lines.put(Either.left(line), OptionalPair.empty());
        return this;
    }

    // ---------- LINE TOUCH ----------
    public HologramBuilder line(String line, OnTouch onTouch) {
        lines.put(Either.left(line), OptionalPair.left(onTouch));
        return this;
    }

    // ---------- TEXT NO TOUCH ----------

    public HologramBuilder text(String text) {
        return text(WordUtils.wrap(text, 50).split("\n"));
    }

    public HologramBuilder text(String... text) {
        return text(Arrays.asList(text));
    }

    public HologramBuilder text(List<String> text) {
        text.forEach(s -> lines.put(Either.left(StringUtils.strip(s)), OptionalPair.empty()));
        return this;
    }

    // ---------- TEXT WITH TOUCH ----------

    public HologramBuilder text(String text, OnTouch touch) {
        return text(touch, text.split("\n"));
    }

    public HologramBuilder text(OnTouch touch, String... text) {
        return text(touch, Arrays.asList(text));
    }

    public HologramBuilder text(OnTouch touch, List<String> text) {
        text.forEach(s -> lines.put(Either.left(s), OptionalPair.left(touch)));
        return this;
    }

    // ---------- ITEM NO TOUCH NO PICKUP ----------

    public HologramBuilder item(ItemStack item) {
        lines.put(Either.right(item), OptionalPair.empty());
        return this;
    }

    // ---------- MULTIPLE ITEMS NO TOUCH NO PICKUP ----------

    public HologramBuilder items(ItemStack... items) {
        return items(Arrays.asList(items));
    }

    public HologramBuilder items(List<ItemStack> items) {
        items.forEach(this::item);
        return this;
    }

    // ---------- ITEM TOUCH NO PICKUP ----------

    public HologramBuilder itemT(ItemStack item, OnTouch touch) {
        lines.put(Either.right(item), OptionalPair.left(touch));
        return this;
    }

    // ---------- MULTIPLE ITEMS TOUCH NO PICKUP ----------

    public HologramBuilder itemsT(OnTouch touch, ItemStack... items) {
        return itemsT(touch, Arrays.asList(items));
    }

    public HologramBuilder itemsT(OnTouch touch, List<ItemStack> items) {
        items.forEach(i -> itemT(i, touch));
        return this;
    }

    // ---------- ITEM NO TOUCH PICKUP ----------

    public HologramBuilder itemP(ItemStack item, OnPickup pickup) {
        lines.put(Either.right(item), OptionalPair.right(pickup));
        return this;
    }

    // ---------- MULTIPLE ITEMS NO TOUCH PICKUP ----------

    public HologramBuilder itemsP(OnPickup pickup, ItemStack... items) {
        return itemsP(pickup, Arrays.asList(items));
    }

    public HologramBuilder itemsP(OnPickup pickup, List<ItemStack> items) {
        items.forEach(i -> itemP(i, pickup));
        return this;
    }

    // ---------- ITEM TOUCH PICKUP ----------

    public HologramBuilder item(ItemStack item, OnTouch touch, OnPickup pickup) {
        lines.put(Either.right(item), OptionalPair.of(touch, pickup));
        return this;
    }

    // ---------- MULTIPLE ITEMS TOUCH PICKUP ----------

    public HologramBuilder items(OnTouch touch, OnPickup pickup, ItemStack... items) {
        return items(touch, pickup, Arrays.asList(items));
    }

    public HologramBuilder items(OnTouch touch, OnPickup pickup, List<ItemStack> items) {
        items.forEach(i -> item(i, touch, pickup));
        return this;
    }

    public Hologram build() {
        Hologram hologram = HologramsAPI.createHologram(plugin, location);
        lines.forEach((k, v) ->
                k.apply(left -> {
                    TextLine line = hologram.appendTextLine(left);

                    if (onTouchDefault != null) line.setTouchHandler(player -> onTouchDefault.onTouch(player, line));

                    v.apply(cons -> line.setTouchHandler(player -> cons.onTouch(player, line)),
                            pickup -> { throw new UnsupportedOperationException("Cannot apply pickup to text line! :("); }); // should never see this - builder should restrict giving a TextLine a OnPickup.

                    if (onTouchOverride != null) line.setTouchHandler(player -> onTouchOverride.onTouch(player, line)); // override any custom onTouchHandler
                    }, right -> {
                    ItemLine line = hologram.appendItemLine(right);

                    if (onTouchDefault != null) line.setTouchHandler(player -> onTouchDefault.onTouch(player, line));
                    if (onPickupDefault != null) line.setPickupHandler(player -> onPickupDefault.onPickup(player, line));

                    v.apply(cons -> line.setTouchHandler(player -> cons.onTouch(player, line)),
                            cons -> line.setPickupHandler(player -> cons.onPickup(player, line)));

                    if (onTouchOverride != null) line.setTouchHandler(player -> onTouchOverride.onTouch(player, line)); // override any custom onTouchHandler
                    if (onPickupOverride != null) line.setPickupHandler(player -> onPickupOverride.onPickup(player, line));
                }));
        return hologram;
    }
}
