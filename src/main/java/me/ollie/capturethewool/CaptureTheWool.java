package me.ollie.capturethewool;

import com.comphenix.protocol.ProtocolLib;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import lombok.Getter;
import me.ollie.capturethewool.commands.CaptureTheWoolCommand;
import me.ollie.capturethewool.core.GamesCore;
import me.ollie.capturethewool.core.hologram.DroppedItemHologram;
import me.ollie.capturethewool.core.hologram.DroppedItemHologramBuilder;
import me.ollie.capturethewool.core.hologram.PaginatedHologram;
import me.ollie.capturethewool.core.hologram.PaginatedHologramBuilder;
import me.ollie.capturethewool.core.pve.DisableSpawners;
import me.ollie.capturethewool.core.hologram.meta.HologramBuilder;
import me.ollie.capturethewool.core.npc.InteractableVillager;
import me.ollie.capturethewool.items.meta.PowerfulItemEvents;
import me.ollie.capturethewool.core.util.particles.ActionOnEnterVortex;
import me.ollie.capturethewool.items.swords.AssassinsBlade;
import org.bukkit.*;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class CaptureTheWool extends JavaPlugin {

    @Getter
    private static CaptureTheWool instance;

    private ProtocolManager protocolManager;

    @Getter
    private GamesCore gamesCore;

    @Override
    public void onLoad() {
        this.protocolManager = ProtocolLibrary.getProtocolManager();

    }
    @Override
    public void onEnable() {
        this.gamesCore = new GamesCore(this, protocolManager);

        this.gamesCore.getHolographicDamageListener().toggle();


        Location loc = new Location(Bukkit.getWorld("world"), 488.5, 7.5, 72.5);
        Location prev = new Location(Bukkit.getWorld("world"), 488, 5.25, 71);
        Location next = new Location(Bukkit.getWorld("world"), 488, 5.25, 74);
//        Hologram hologram = new HologramBuilder(this, loc)
//                .text(WordUtils.wrap("According to all known laws of aviation, " +
//                        "there's no way a bee should be able to fly." +
//                        " Its wings are too small to get its fat little body off the ground." +
//                        " The bee, of course, flies anyways.", 100))
//                .onTouchOverride((p, line) -> p.sendMessage("hi! You touched: " + ((TextLine) line).getText())).build();

        PaginatedHologram paginatedHologram = new PaginatedHologramBuilder(this)
                .title(ChatColor.AQUA + "" + ChatColor.BOLD + "Bee Movie Script")
                .pagesLocation(loc)
                .nextButton(next)
                .prevButton(prev)
                .page(new HologramBuilder(this, loc)
                        .text("According to all known laws of aviation, there's no way a bee should be able to fly. Its wings are too small to get its fat little body off the ground. The bee, of course, flies anyways. Because bees don't care what humans think is impossible.")
                        .build())
                .page(new HologramBuilder(this, loc).text("Yellow black, yellow black, yellow black, yellow black, yellow black, ooo black and yellow lets shake it up a little!").build())
                .page(new HologramBuilder(this, loc).text("Barry, breakfast is ready! Coming! Hang on a second. Hey Barry, Hey Adam.").build())
                .page(new HologramBuilder(this, loc).text("Can you believe this is happening? No I can't I'll pick you up!").build())
                .page(new HologramBuilder(this, loc).text("Barry use the stairs your father paid good money for those! Sorry, I'm excited!").build())
                .page(new HologramBuilder(this, loc).text("yan you really do suck").build())
                .build();

        paginatedHologram.init();

        InteractableVillager rightVillager = new InteractableVillager(this, new Location(Bukkit.getWorld("world"), 467.5, 4, 60.5), Villager.Profession.NONE, ChatColor.AQUA + "Capture the Wool", ChatColor.YELLOW + "" + ChatColor.BOLD + "RIGHT CLICK", p -> p.sendMessage("hi"));
        InteractableVillager leftVillager = new InteractableVillager(this, new Location(Bukkit.getWorld("world"), 479.5, 4, 60.5), Villager.Profession.NONE, ChatColor.AQUA + "Capture the Wool", ChatColor.YELLOW + "" + ChatColor.BOLD + "RIGHT CLICK", p -> p.sendMessage("hi"));

        DroppedItemHologram hologram = DroppedItemHologramBuilder.create(this, new Location(Bukkit.getWorld("world"), 473.5, 11, 92.5))
                .item(new ItemStack(Material.DIRT).asQuantity(3))
                .audience(Bukkit.getOnlinePlayers())
                .onPickup((p, i) -> p.sendMessage("hi!!"))
                .build();
        // elevator
        ActionOnEnterVortex actionOnEnterVortex = new ActionOnEnterVortex(this,
                Particle.PORTAL,
                new Location(Bukkit.getWorld("world"), 410.5, 4, 70.5),
                20,
                4,
                12,
                player -> player.setVelocity(new Vector(player.getVelocity().getX(), 0.4, player.getVelocity().getZ())));

        actionOnEnterVortex.setDescending(true);

        actionOnEnterVortex.run();

        registerCommands();
        registerEvents();
        instance = this;
    }

    private void registerCommands() {
        getCommand("ctw").setExecutor(new CaptureTheWoolCommand());
    }

    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new AssassinsBlade(), this);
        getServer().getPluginManager().registerEvents(new DisableSpawners(), this);
        getServer().getPluginManager().registerEvents(new PowerfulItemEvents(), this);
    }



    @Override
    public void onDisable() {
        InteractableVillager.destroyAll();
    }
}
