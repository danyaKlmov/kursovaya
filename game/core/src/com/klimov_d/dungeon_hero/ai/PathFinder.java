package com.klimov_d.dungeon_hero.ai;

import com.klimov_d.dungeon_hero.Position;
import com.klimov_d.dungeon_hero.Stage;

public interface PathFinder {
    Path findPath(Stage stage, Position posFrom, Position posTo);
}
