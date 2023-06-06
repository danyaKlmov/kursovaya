package com.klimov_d.dungeon_hero.ai;

import com.klimov_d.dungeon_hero.Position;
import com.klimov_d.dungeon_hero.Stage;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class WavePathFinder implements PathFinder {

    @Override
    public Path findPath(Stage stage, Position posFrom, Position posTo) {
        int distances[][] = new int[stage.getHeight()][stage.getWidth()];
        Queue<Position> positions = new ArrayDeque<Position>();
        initDistances(stage, distances);
        distances[posFrom.getLine()][posFrom.getColumn()] = 0;
        positions.add(posFrom);
        boolean pathFound = false;
        while (!positions.isEmpty() && !pathFound) {
            pathFound = pathFoundStep(positions, posTo, distances, stage);
        }
        if (pathFound) {
            return restorePath(distances, posFrom, posTo, stage);
        }
        return null;
    }

    private Path restorePath(int[][] distances, Position pathFrom, Position pathTo, Stage stage) {
        Path path = new Path();
        Position current = pathTo;
        while(current.getLine() != pathFrom.getLine() || current.getColumn() != pathFrom.getColumn()) {
            path.addPointToBegin(current);
            List<Position> neighbours = stage.getNeighbours(current);
            for(int i = 0; i < neighbours.size(); i++) {
                Position neighbour = neighbours.get(i);
                if(distances[current.getLine()][current.getColumn()] ==
                        distances[neighbour.getLine()][neighbour.getColumn()] + 1) {
                    current = neighbour;
                    break;
                }
            }
        }
        path.addPointToBegin(current);
        return path;
    }

    private boolean pathFoundStep(Queue<Position> positions, Position posTo, int[][] distances, Stage stage) {
        Position position = positions.poll();
        if (position.getLine() == posTo.getLine() && position.getColumn() == posTo.getColumn()) {
            return true;
        }
        List<Position> neighbours = stage.getNeighbours(position);
        for (int i = 0; i < neighbours.size(); i++) {
            Position neighbour = neighbours.get(i);
            if (distances[neighbour.getLine()][neighbour.getColumn()] < 0 &&
                stage.cellIsExist(neighbour) &&
                stage.isPassable(neighbour) &&
                stage.noMonsters(neighbour)) {
                distances[neighbour.getLine()][neighbour.getColumn()] =
                        distances[position.getLine()][position.getColumn()] + 1;
                positions.add(neighbour);
            }
        }
        return false;
    }

    private static void initDistances(Stage stage, int[][] distances) {
        for (int i = 0; i < stage.getHeight(); i++) {
            for (int a = 0; a < stage.getWidth(); a++) {
                distances[i][a] = -1;
            }
        }
    }
}
