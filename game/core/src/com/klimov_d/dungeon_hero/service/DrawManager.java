package com.klimov_d.dungeon_hero.service;

import com.klimov_d.dungeon_hero.Character;
import com.klimov_d.dungeon_hero.Position;

public interface DrawManager {
    void putSymbol(char symbol, Position position);
    void addMessage(String message);
    void putPerson(Character character);
    void drawAllSymbols(float x, float y);
    void clear();
    void clearMessages();
}
