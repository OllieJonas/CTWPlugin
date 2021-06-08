package me.ollie.capturethewool.commands;

import me.ollie.capturethewool.commands.subcommands.*;
import me.ollie.capturethewool.core.command.RootCommand;

public class CaptureTheWoolCommand extends RootCommand {

    public CaptureTheWoolCommand() {
        super("capturethewool", "ctw");
    }

    @Override
    public void addSubCommands() {
        addSubCommand(new YanSucksCommand());
        addSubCommand(new ProgressBarCommand());
        addSubCommand(new PowerfulItemCommand());
        addSubCommand(new ExoticCiphersCommand());
        addSubCommand(new ExoticShopCommand());
        addSubCommand(new ZombieCommand());
        addSubCommand(new SpawnerCommand());
        addSubCommand(new ImageCommand());
        addSubCommand(new EmojiCommand());
        addSubCommand(new GCFreeCommand());
        addSubCommand(new FakeCollectCommand());
        addSubCommand(new AbilityCommand());
        addSubCommand(new VectorCircleCommand());
        addSubCommand(new BossCommand());
        addSubCommand(new KeyCommand());
        addSubCommand(new ExplosionCommand());
        addSubCommand(new CannonCommand());
        addSubCommand(new EntityRiseGroundCommand());
    }
}
