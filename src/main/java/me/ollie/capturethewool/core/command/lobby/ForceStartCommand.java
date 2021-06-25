package me.ollie.capturethewool.core.command.lobby;

import me.ollie.capturethewool.core.command.meta.ISubCommand;
import me.ollie.capturethewool.core.command.meta.annotations.CommandAliases;
import me.ollie.capturethewool.core.command.meta.annotations.OperatorCommand;
import me.ollie.capturethewool.core.command.meta.annotations.SubCommand;
import me.ollie.capturethewool.core.command.meta.internal.context.SubCommandContext;
import me.ollie.capturethewool.core.lobby.Lobby;
import me.ollie.capturethewool.core.lobby.LobbyManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

@SubCommand(name = "forcestart", root = LobbyCommand.class)
@OperatorCommand
@CommandAliases({"fs", "fstart"})
public class ForceStartCommand implements ISubCommand {

    @Override
    public void execute(Player player, SubCommandContext context) {
        Lobby lobby = LobbyManager.getInstance().getLobbyFor(player);

        if (lobby == null) {
            player.sendMessage(ChatColor.RED + "You are not currently in a lobby!");
            return;
        }

        lobby.sendMessageToAll(ChatColor.DARK_AQUA + "This game has been force started by " + ChatColor.AQUA + player.getName());
        lobby.gameStarting();
    }
}
