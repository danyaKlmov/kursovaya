package com.klimov_d.dungeon_hero.ai;

import com.klimov_d.dungeon_hero.Dungeon;
import com.klimov_d.dungeon_hero.Monster;

public interface MonsterController {
    void action(Dungeon dungeon, Monster monster);
}
