package com.klimov_d.dungeon_hero.service;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.klimov_d.dungeon_hero.Character;
import com.klimov_d.dungeon_hero.Position;

import java.util.ArrayList;

public class SpriteBatchDrawManager implements DrawManager {
    private SpriteBatch batch;
    private BitmapFont font;
    private char[][] symbols;
    private int height;
    private int width;
    private ArrayList<String> messages;
    private boolean needClearMessages;
    private Character person;

    public SpriteBatchDrawManager(
            SpriteBatch batch,
            BitmapFont font,
            int height,
            int width
    ) {
        this.batch = batch;
        this.font = font;
        symbols = new char[height][width];
        this.height = height;
        this.width = width;
        messages = new ArrayList<>();
        person = null;
    }

    @Override
    public void putSymbol(char symbol, Position position) {
        symbols[position.getLine()][position.getColumn()] = symbol;
    }

    @Override
    public void addMessage(String message) {
        messages.add(message);
    }

    @Override
    public void putPerson(Character person) {
        this.person = person;
    }

    @Override
    public void drawAllSymbols(float x, float y) {
        batch.begin();
        font.draw(batch, buildString(), x, y);
        batch.end();
    }

    private String buildString() {
        StringBuilder builder = new StringBuilder();
        ArrayList<String> personLines = buildPersonLines();
        for (int i = 0; i < height; ++i) {
            builder
                .append(symbols[i])
                .append("   ")
                .append(i < personLines.size() ? personLines.get(i) : "")
                .append('\n');
        }
        for (int i = 0; i < messages.size(); ++i) {
            builder.append('\n');
            builder.append(messages.get(i));
        }
        return builder.toString();
    }

    private ArrayList<String> buildPersonLines() {
        ArrayList<String> lines = new ArrayList<>();
        lines.add("Персонаж: " + person.toString());
        lines.add("Надето");
        person.getArmorSlot().ifPresent(a -> lines.add(a.toString()));
        person.getHelmetSlot().ifPresent(h -> lines.add(h.toString()));
        person.getAmuletSlot().ifPresent(a -> lines.add(a.toString()));
        person.getBootsSlot().ifPresent(b -> lines.add(b.toString()));
        person.getTrousersSlot().ifPresent(t -> lines.add(t.toString()));
        person.getRingSlot(0).ifPresent(r -> lines.add(r.toString()));
        person.getRingSlot(1).ifPresent(r -> lines.add(r.toString()));
        person.getWeaponSlot(0).ifPresent(w -> lines.add(w.toString()));
        person.getWeaponSlot(1).ifPresent(w -> lines.add(w.toString()));
        lines.add("Сокровищ на сумму " + person.getTreasuresCost());

        return lines;
    }

    @Override
    public void clear() {
        for (int i = 0; i < height; ++i)
            for(int j = 0; j < width; ++j)
                symbols[i][j] = ' ';
    }

    @Override
    public void clearMessages() {
        messages.clear();
    }
}
