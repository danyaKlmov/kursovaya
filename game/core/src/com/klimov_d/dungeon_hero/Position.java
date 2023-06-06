package com.klimov_d.dungeon_hero;

import java.util.Objects;

public class Position {
    private int line;
    private int column;

    public Position(int line, int column) {
        this.line = line;
        this.column = column;
    }

    public Position() {
        line = 0;
        column = 0;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Position position)) return false;
        return line == position.line && column == position.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(line, column);
    }
}
