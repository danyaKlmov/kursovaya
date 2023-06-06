package com.klimov_d.dungeon_hero;

import com.klimov_d.dungeon_hero.ai.AggressiveMonsterController;
import com.klimov_d.dungeon_hero.ai.MonsterController;
import com.klimov_d.dungeon_hero.service.DrawManager;

public class Monster extends Character {
    private String name;
    MonsterController controller;

    public Monster(
            int attack,
            int armor,
            int damage,
            int defence,
            int health,
            int strength,
            String name,
            int distanceOfSens
    ) {
        super(attack, armor, damage, defence, health, strength);
        this.name = name;
        this.controller = new AggressiveMonsterController(distanceOfSens);
    }

    public String getName() {
        return name;
    }

    @Override
    public void draw(DrawManager drawManager) {
        drawManager.putSymbol(name.charAt(0), getPosition());
    }
    public void action(Dungeon dungeon) {
        controller.action(dungeon, this);
    }

    @Override
    public String toString() {
        return String.format("%s %s", name, super.toString());
    }
}
