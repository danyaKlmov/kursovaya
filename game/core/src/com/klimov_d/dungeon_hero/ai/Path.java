package com.klimov_d.dungeon_hero.ai;

import com.klimov_d.dungeon_hero.Position;

import java.util.ArrayList;
import java.util.List;

public class Path {
    private List<Position> points;

    public Path() {
        this.points = new ArrayList<>();
    }
    public void addPointToEnd(Position point) {
        points.add(0, point);
    }
    public void addPointToBegin(Position point) {
        points.add(point);
    }
    public int length() {
        return points.size();
    }
    public Position getPoint(int index) {
        return points.get(points.size() - 1 - index);
    }
    public Position getNextPoint() {
        if(length() > 1)
            return getPoint(1);
        return getPoint(0);
    }
}
