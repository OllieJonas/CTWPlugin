package me.ollie.capturethewool.core.lobby;

import lombok.Getter;
import lombok.Setter;
import me.ollie.capturethewool.core.map.AbstractGameMap;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Getter
public class MapVote {

    private final Collection<AbstractGameMap> maps;
    private final Map<AbstractGameMap, Integer> talley;
    private final Map<Player, AbstractGameMap> votes;
    @Setter
    private boolean canVote = true;

    public MapVote(Collection<AbstractGameMap> maps) {
        this.maps = maps;
        this.talley = new ConcurrentHashMap<>();
        this.votes = new HashMap<>();

        maps.forEach(m -> talley.put(m, 0));
    }

    public void vote(Player player, AbstractGameMap map) {
        if (canVote) {
            if (hasPlayerAlreadyVoted(player)) {
                AbstractGameMap mapVotedFor = votes.get(player);
                if (mapVotedFor == map) {
                    player.sendMessage(ChatColor.GRAY + "You already voted for this map!");
                    return;
                } else {
                    removeVote(mapVotedFor);
                }
            }

            votes.put(player, map);
            addVote(map);
        } else {
            player.sendMessage(ChatColor.GRAY + "You can't vote right now! :(");
        }
    }

    public AbstractGameMap getLeadingMap() {
        List<AbstractGameMap> sorted = talley.entrySet()
                .stream()
                .sorted(Comparator.comparingInt(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        Collections.reverse(sorted);
        return sorted.get(0);
    }

    private boolean hasPlayerAlreadyVoted(Player player) {
        return votes.containsKey(player);
    }

    private void addVote(AbstractGameMap map) {
        talley.put(map, talley.get(map) + 1);
    }

    private void removeVote(AbstractGameMap map) {
        talley.put(map, talley.get(map) - 1);
    }
}
