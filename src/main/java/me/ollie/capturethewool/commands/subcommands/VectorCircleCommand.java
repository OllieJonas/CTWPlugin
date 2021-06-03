package me.ollie.capturethewool.commands.subcommands;

import me.ollie.capturethewool.CaptureTheWool;
import me.ollie.capturethewool.core.command.SubCommand;
import me.ollie.capturethewool.core.util.VectorCircle;
import me.ollie.capturethewool.core.util.stream.Permutations;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.entity.Bee;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class VectorCircleCommand extends SubCommand {

    public VectorCircleCommand() {
        super("vectorcircle", true);
    }

    @Override
    public void execute(Player player, String aliasUsed, List<String> args) {

        List<Double> doubles = new ArrayList<>();
        doubles.add(-0.3);
        doubles.add(-0.8);
        doubles.add(0.1);
        doubles.add(0.7);

        AtomicLong aLong = new AtomicLong(1);

        List<Vector> axes = List.of(new Vector(0, 1, 0));
//         List<Vector> axes = Permutations.of(doubles, 3).map(p -> new Vector(p.get(0), p.get(1), p.get(2))).toList();
         List<Bee> bees = IntStream.range(0, 3).mapToObj(i -> player.getWorld().spawn(player.getEyeLocation(), Bee.class)).toList();

         List<VectorCircle> circles = bees.stream().map(bee -> new VectorCircle(player, axes.get(0), 0.1, 2.75, bee::teleport)).toList();
//        List<VectorCircle> circles = Permutations.of(doubles, 3).map(p -> new Vector(p.get(0), p.get(1), p.get(2))).map(v -> new VectorCircle(player, v, 0.2, 2.75, l -> l.getWorld().spawnParticle(Particle.REDSTONE, l, 1, new Particle.DustOptions(Color.PURPLE, 1)))).collect(Collectors.toList());
        player.sendMessage("" + circles.size());
        circles.forEach(c -> c.runTaskTimer(CaptureTheWool.getInstance(), aLong.getAndAdd(10), 1L));
    }
}
