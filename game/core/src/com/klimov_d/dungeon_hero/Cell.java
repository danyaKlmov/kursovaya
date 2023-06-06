package com.klimov_d.dungeon_hero;

import com.klimov_d.dungeon_hero.items.Item;
import com.klimov_d.dungeon_hero.service.DrawManager;
import com.klimov_d.dungeon_hero.service.Drawable;

import java.util.ArrayList;
import java.util.List;

public class Cell implements Drawable {
    private Position position;
    private List<Item> items;
    private char symbol;
    private boolean passability;

    public Cell(Position position, char symbol, boolean passability) {
        this.position = position;
        this.symbol = symbol;
        this.passability = passability;
        this.items = new ArrayList<>();
    }

    public Position getPosition() {
        return position;
    }
    public void setPosition(Position value) {
        position = value;
    }

    public List<Item> getItems() {
        return new ArrayList<>(items);
    }

    public char getSymbol() {
        return symbol;
    }

    public boolean isPassable() {
        return passability;
    }

    @Override
    public void draw(DrawManager drawManager) {
        drawManager.putSymbol(symbol, position);
        for (var item: items) {
            item.draw(drawManager);
        }
    }
}
