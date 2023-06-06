package com.klimov_d.dungeon_hero.items;

import com.klimov_d.dungeon_hero.Character;

public class Treasure extends Item {
    private int cost;

    public Treasure(int weight, String name, int cost) {
        super(weight, name);
        this.cost = cost;
    }

    @Override
    public void use(Character character) {}
    public int getCost() {
        return cost;
    }
    @Override
    public String toString() {
        return String.format(
                "%s стоимостью %d",
                super.toString(),
                cost
        );
    }

    public void take(Character person) {
        person.addTreasure(this);
    }
}
