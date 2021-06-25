package me.ollie.capturethewool.core.command.lobby;

import me.ollie.capturethewool.core.command.meta.ISubCommand;
import me.ollie.capturethewool.core.command.meta.annotations.CommandAliases;
import me.ollie.capturethewool.core.command.meta.annotations.CommandInfo;
import me.ollie.capturethewool.core.command.meta.annotations.SubCommand;
import me.ollie.capturethewool.core.command.meta.internal.context.SubCommandContext;
import me.ollie.capturethewool.core.lobby.Lobby;
import me.ollie.capturethewool.core.lobby.LobbyManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

@SubCommand(name = "leave", root = LobbyCommand.class)
@CommandAliases("l")
@CommandInfo(
        shortDescription = "Leave the current lobby you're in"
)
public class LeaveCommand implements ISubCommand {

    @Override
    public void execute(Player player, SubCommandContext context) {
        Lobby curr = LobbyManager.getInstance().getLobbyFor(player);
        if (curr == null) {
            player.sendMessage(ChatColor.RED + "You're not in a lobby!");
            return;
        }

        LobbyManager.getInstance().leaveLobby(player);
    }
}
