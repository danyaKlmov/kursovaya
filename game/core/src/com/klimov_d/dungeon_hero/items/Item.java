package com.klimov_d.dungeon_hero.items;

import com.klimov_d.dungeon_hero.Character;
import com.klimov_d.dungeon_hero.Position;
import com.klimov_d.dungeon_hero.service.DrawManager;
import com.klimov_d.dungeon_hero.service.Drawable;

public abstract class Item implements Drawable {
    private int weight;
    private String name;
    private Position position;

    public Item(int weight, String name) {
        this.weight = weight;
        this.name = name;
        this.position = new Position();
    }

    public int getWeight() {
        return weight;
    }

    public String getName() {
        return name;
    }

    public abstract void use(Character character);

    public void setPosition(Position pos) {
        position = pos;
    }

    public Position getPosition() {
        return position;
    }

    @Override
    public void draw(DrawManager drawManager) {
        drawManager.putSymbol(name.toLowerCase().charAt(0), position);
    }

    @Override
    public String toString() {
        return name;
    }
}
