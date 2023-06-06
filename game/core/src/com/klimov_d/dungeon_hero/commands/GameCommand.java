package com.klimov_d.dungeon_hero.commands;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.klimov_d.dungeon_hero.DungeonHeroGame;

import java.util.Map;
import java.util.Optional;

public abstract class GameCommand implements Command {
    private final static Map<Integer, Command> commands = Map.of(
            Input.Keys.W, new UpCommand(),
            Input.Keys.A, new LeftCommand(),
            Input.Keys.S, new DownCommand(),
            Input.Keys.D, new RightCommand(),
            Input.Keys.SPACE, new PassCommand(),
            Input.Keys.ESCAPE, new ExitCommand(),
            Input.Keys.E, new ActionCommand()
    );

    public static Optional<Command> get() {
        for (var key: commands.keySet()) {
            if (Gdx.input.isKeyJustPressed(key))
                return Optional.of(commands.get(key));
        }
        return Optional.empty();
    }

    private static class UpCommand extends GameCommand {
        @Override
        public void apply() {
            DungeonHeroGame.instance().movePersonUp();
        }
    }
    private static class DownCommand extends GameCommand {
        @Override
        public void apply() {
            DungeonHeroGame.instance().movePersonDown();
        }
    }
    private static class LeftCommand extends GameCommand {
        @Override
        public void apply() {
            DungeonHeroGame.instance().movePersonLeft();
        }
    }
    private static class RightCommand extends GameCommand {
        @Override
        public void apply() {
            DungeonHeroGame.instance().movePersonRight();
        }
    }
    private static class ActionCommand extends GameCommand {
        @Override
        public void apply() {
            DungeonHeroGame.instance().personAction();
        }
    }
    private static class PassCommand extends GameCommand {
        @Override
        public void apply() {}
    }
    private static class ExitCommand extends GameCommand {
        @Override
        public void apply() {
            Gdx.app.exit();
        }
    }
}
