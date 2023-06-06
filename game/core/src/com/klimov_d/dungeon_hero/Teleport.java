package com.klimov_d.dungeon_hero;

import com.klimov_d.dungeon_hero.service.DrawManager;
import com.klimov_d.dungeon_hero.service.Drawable;

public class Teleport implements Drawable {
    private static final char SYMBOL = '>';
    private Stage toStage;
    private Position position;

    public Teleport(Position position) {
        this.position = position;
    }

    public Stage getToStage() {
        return toStage;
    }
    public void setToStage(Stage stage) {
        toStage = stage;
    }

    public Position getPosition() {
        return position;
    }

    @Override
    public void draw(DrawManager drawManager) {
        drawManager.putSymbol(SYMBOL, position);
    }

    @Override
    public String toString() {
        return "Телепорт";
    }
}
