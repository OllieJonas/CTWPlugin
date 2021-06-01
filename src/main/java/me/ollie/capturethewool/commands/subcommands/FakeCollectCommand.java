package me.ollie.capturethewool.commands.subcommands;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import me.ollie.capturethewool.core.GamesCore;
import me.ollie.capturethewool.core.command.SubCommand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;

public class FakeCollectCommand extends SubCommand {

    public FakeCollectCommand() {
        super("fakecollect", true);
    }
    @Override
    public void execute(Player player, String aliasUsed, List<String> args) {
        player.getNearbyEntities(10, 10, 10).stream().peek(e -> player.sendMessage(e.getType().name() + " " + e.getEntityId())).filter(e -> e.getType() == EntityType.DROPPED_ITEM).forEach(e -> playPacket(player, e));
    }

    private void playPacket(Player player, Entity entity) {
        ProtocolManager manager = GamesCore.getInstance().getProtocolManager();
        PacketContainer container = new PacketContainer(PacketType.Play.Server.COLLECT);
        int itemId = entity.getEntityId();
        if (itemId == -1) return;

        container.getIntegers()
                .write(0, itemId)
                .write(1, player.getEntityId());

        try {
            manager.sendServerPacket(player, container);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
