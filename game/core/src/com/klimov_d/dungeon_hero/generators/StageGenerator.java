package com.klimov_d.dungeon_hero.generators;

import com.klimov_d.dungeon_hero.Orientation;
import com.klimov_d.dungeon_hero.Position;
import com.klimov_d.dungeon_hero.Stage;
import com.klimov_d.dungeon_hero.service.RandomSource;

import java.util.ArrayList;
import java.util.Random;

public class StageGenerator {
    public static final int MIN_STAGE_HEIGHT = 20;
    public static final int MAX_STAGE_HEIGHT = 35;
    public static final int MIN_STAGE_WIDTH = 40;
    public static final int MAX_STAGE_WIDTH = 60;
    private static final int EXPAND_PROB = 50;


    private static final int MIN_ROOM_HEIGHT = 3;
    private static final int MAX_ROOM_HEIGHT = 8;
    private static final int MIN_ROOM_WIDTH = 3;
    private static final int MAX_ROOM_WIDTH = 8;

    private static final int ROOM_PLACE_PROB = 10;
    private static final int PLACE_PROB_TEST_MAX = 1000;

    private boolean[] verticalPlaced;
    private boolean[] horizontalPlaced;
    private int stageHeight;
    private int stageWidth;
    private int lastLine;
    private int lastCol;
    private int difficulty;
    private CellType[][] placed;
    private Stage stage;
    private int countOfRooms;
    private ArrayList<RoomArea> rooms;

    public StageGenerator(int difficulty) {
        this.difficulty = difficulty;
    }

    public Stage generate() {
        initialize();
        placeRooms();
        connectRooms();
        stage.cutEmptyCells();
        return stage;
    }

    private void placeRooms() {
        RoomArea roomArea = getNextArea();
        while (roomArea != null) {
            placeRoom(roomArea);
            rooms.add(roomArea);
            roomArea = getNextArea();
        }
    }

    private void connectRooms() {
        for(int i = 0; i < rooms.size(); i++) {
            for(int j = i + 1; j < rooms.size(); j++) {
                connectRoomPair(rooms.get(i), rooms.get(j));
            }
        }
    }
    private void connectRoomPair(RoomArea roomArea1, RoomArea roomArea2) {
        if(connectRoomPairHorizontal(roomArea1, roomArea2)) {
            return;
        }
        connectRoomPairVertical(roomArea1, roomArea2);
    }
    private boolean connectRoomPairHorizontal(RoomArea roomArea1, RoomArea roomArea2) {
        if(roomArea1.left > roomArea2.left) {
            RoomArea temp = roomArea1;
            roomArea1 = roomArea2;
            roomArea2 = temp;
        }
        ArrayList<Line> verticalIntersection = getVerticalIntersection(roomArea1, roomArea2);
        if(verticalIntersection != null ) {
            placeHorizontalRoute(roomArea1, roomArea2, verticalIntersection);
            return true;
        }
        return false;
    }
    private void connectRoomPairVertical(RoomArea roomArea1, RoomArea roomArea2) {
        if(roomArea1.top > roomArea2.top) {
            RoomArea temp = roomArea1;
            roomArea1 = roomArea2;
            roomArea2 = temp;
        }
        ArrayList<Line> horizontalIntersection = getHorizontalIntersection(roomArea1, roomArea2);
        if(horizontalIntersection != null ) {
            placeVerticalRoute(roomArea1, roomArea2, horizontalIntersection);
        }
    }
    private ArrayList<Line> getVerticalIntersection(RoomArea left, RoomArea right) {
        if(left.left + left.width >= right.left) {
            return null;
        }
        if(left.top + left.height <= right.top || right.top + right.height <= left.top) {
            return null;
        }
        Line line = getVerticalConnectionLine(left, right);
        return getVerticalIntersectionLines(left, right, line);
    }

    private ArrayList<Line> getVerticalIntersectionLines(RoomArea left, RoomArea right, Line line) {
        ArrayList<Line> lines = new ArrayList<>();
        Line line1 = new Line(line.start, 0);
        for(int i = 0; i < line.length; i++) {
            boolean pl = false;
            for(int k = left.left + left.width + 1; k < right.left - 1; k++) {
                if(placed[line.start + i][k] != CellType.FREE) {
                    pl = true;
                    if(line1.length > 0) {
                        lines.add(line1);
                    }
                    line1 = new Line(line.start + i + 1, 0);
                    break;
                }
            }
            if(!pl)
                line1.length++;
        }
        if(line1.length > 0)
            lines.add(line1);
        return lines;
    }

    private Line getIntersectionOfLines(Line line1, Line line2) {
        if(line1.start <= line2.start &&
           line1.start + line1.length <= line2.start + line1.length)
            return new Line(line2.start, line1.start + line1.length - line2.start);
        if(line2.start <= line1.start &&
                line2.start + line2.length <= line1.start + line1.length)
            return new Line(line1.start, line2.start + line2.length - line1.start);
        if(line1.start < line2.start &&
            line1.start + line1.length >= line2.start + line1.length)
            return new Line(line2.start, line2.length);
        return new Line(line1.start, line1.length);
    }

    private Line getVerticalConnectionLine(RoomArea left, RoomArea right) {
        return getIntersectionOfLines(
            new Line(left.top, left.height),
            new Line(right.top, right.height)
        );
    }
    private ArrayList<Line> getHorizontalIntersectionLines(RoomArea top, RoomArea bottom, Line line) {
        ArrayList<Line> lines = new ArrayList<>();
        Line line1 = new Line(line.start, 0);
        for(int i = 0; i < line.length; i++) {
            boolean pl = false;
            for(int k = top.top + top.height + 1; k < bottom.top - 1; k++) {
                if(placed[k][line.start + i] != CellType.FREE) {
                    pl = true;
                    if(line1.length > 0) {
                        lines.add(line1);
                    }
                    line1 = new Line(line.start + i + 1, 0);
                    break;
                }
            }
            if(!pl)
                line1.length++;
        }
        if(line1.length > 0)
            lines.add(line1);
        return lines;
    }

    private Line getHorizontalConnectionLine(RoomArea top, RoomArea bottom) {
        return getIntersectionOfLines(
            new Line(top.left, top.width),
            new Line(bottom.left, bottom.width)
        );
    }

    private void placeHorizontalRoute(RoomArea left, RoomArea right, ArrayList<Line> verticalIntersection) {
        if(verticalIntersection.isEmpty()) {
          return;
        }
        int lineNumber = RandomSource.getRandom().nextInt(verticalIntersection.size());
        Line line = verticalIntersection.get(lineNumber);
        int routePos = RandomSource.getRandom().nextInt(line.start, line.start + line.length);
        Position posFrom = new Position(routePos, left.left + left.width);
        Position posTo = new Position(routePos, right.left - 1);
        stage.addTunnel(posFrom, posTo, Orientation.Horizontal);
    }

    private ArrayList<Line> getHorizontalIntersection(RoomArea top, RoomArea bottom) {
        if(top.top + top.height >= bottom.top) {
            return null;
        }
        if(top.left + top.width <= bottom.left || bottom.left + bottom.width <= top.left) {
            return null;
        }
        Line line = getHorizontalConnectionLine(top, bottom);
        return getHorizontalIntersectionLines(top, bottom, line);
    }

    private void placeVerticalRoute(RoomArea top, RoomArea bottom, ArrayList<Line> horizontalIntersection) {
        if(horizontalIntersection.isEmpty()) {
            return;
        }
        int lineNumber = RandomSource.getRandom().nextInt(horizontalIntersection.size());
        Line line = horizontalIntersection.get(lineNumber);
        int routePos = RandomSource.getRandom().nextInt(line.start, line.start + line.length);
        Position posFrom = new Position(top.top + top.height, routePos);
        Position posTo = new Position(bottom.top - 1, routePos);
        stage.addTunnel(posFrom, posTo, Orientation.Vertical);
    }

    private void placeRoom(RoomArea roomArea) {
        stage.addRoom(new Position(roomArea.top - 1, roomArea.left - 1),
                new Position(roomArea.height + 2, roomArea.width + 2));
        for (int line = roomArea.top; line < roomArea.top + roomArea.height; line++) {
            for (int col = roomArea.left; col < roomArea.left + roomArea.width; col++) {
                placed[line][col] = CellType.ROOM;
                verticalPlaced[line] = true;
                horizontalPlaced[col] = true;
            }
        }
        for (int line = roomArea.top - 1; line < roomArea.top + roomArea.height + 1; line++) {
            placed[line][roomArea.left - 1] = CellType.WALL;
            placed[line][roomArea.left + roomArea.width] = CellType.WALL;
        }
        for (int col = roomArea.left - 1; col < roomArea.left + roomArea.width + 1; col++) {
            placed[roomArea.top - 1][col] = CellType.WALL;
            placed[roomArea.top + roomArea.height][col] = CellType.WALL;
        }
        countOfRooms++;
    }

    private void initialize() {
        rooms = new ArrayList<>();
        lastCol = 1;
        lastLine = 1;
        countOfRooms = 0;
        stageHeight = RandomSource.getRandom().nextInt(MIN_STAGE_HEIGHT, MAX_STAGE_HEIGHT + 1);
        stageWidth = RandomSource.getRandom().nextInt(MIN_STAGE_WIDTH, MAX_STAGE_WIDTH + 1);
        stage = new Stage(stageHeight, stageWidth, difficulty);
        verticalPlaced = new boolean[stageHeight];
        horizontalPlaced = new boolean[stageWidth];
        placed = new CellType[stageHeight][stageWidth];
        for(int line = 0; line < stageHeight; line++) {
            for(int col = 0; col < stageWidth; col++) {
                placed[line][col] = CellType.FREE;
            }
        }
    }

    private RoomArea getNextArea() {
        for (int line = lastLine; line < stageHeight - 1; line++, lastLine++) {
            for (int col = lastCol; col < stageWidth - 1; col++, lastCol++) {
                RoomArea startArea = cellFitForRoomStart(line, col);
                if (startArea != null) {
                    return buildRoomArea(startArea);
                }
            }
            lastCol = 1;
        }
        return null;
    }

    private RoomArea cellFitForRoomStart(int startLine, int startCol) {
        if (!placeProbTest()) {
            return null;
        }
        Position iPos = new Position(-1, -1);
        boolean intersects = isIntersects(startLine, startCol, iPos);
        boolean fitSize = isFitSize(startLine, startCol, intersects, iPos.getLine(), iPos.getColumn());
        if (fitSize && intersects) {
            RoomArea roomArea = new RoomArea(startCol,
                    startLine,
                    Math.max(MIN_ROOM_WIDTH, iPos.getColumn() - startCol + 1),
                    Math.max(MIN_ROOM_HEIGHT, iPos.getLine() - startLine + 1));
            if(roomArea.left + roomArea.width > stageWidth - 2 || roomArea.top + roomArea.height > stageHeight - 2) {
                return null;
            }
            return roomArea;
        }
        return null;
    }

    private boolean isIntersects(int startLine, int startCol, Position iPos) {
        boolean intersects = countOfRooms == 0;
        for (int line = startLine; line < stageHeight && line < startLine + MAX_ROOM_HEIGHT; line++) {
            for (int col = startCol; col < stageWidth && col < startCol + MAX_ROOM_WIDTH; col++) {
                if (verticalPlaced[line] || horizontalPlaced[col]) {
                    intersects = true;
                    iPos.setLine(line);
                    iPos.setColumn(col);
                    break;
                }
            }
            if (intersects) {
                break;
            }
        }
        return intersects;
    }

    private boolean isFitSize(int startLine, int startCol, boolean intersects, int iLine, int iCol) {
        boolean fitSize = true;
        for (int line = startLine;
             line < stageHeight && intersects && line < Math.max(iLine, startLine + MIN_ROOM_HEIGHT);
             line++) {
            for (int col = startCol; col < stageWidth && col < Math.max(iCol, startCol + MIN_ROOM_WIDTH); col++) {
                if (placed[line][col] != CellType.FREE) {
                    fitSize = false;
                    break;
                }
            }
            if(!fitSize) {
               break;
            }
        }
        return fitSize;
    }

    private RoomArea buildRoomArea(RoomArea startArea) {
        while(canBeExpandRight(startArea)) {
            expandRight(startArea);
        }
        while(canBeExpandDown(startArea)) {
            expandDown(startArea);
        }
        return startArea;
    }

    private void expandDown(RoomArea startArea) {
        startArea.height++;
    }

    private boolean canBeExpandDown(RoomArea startArea) {
        if(!expandProbTest() || startArea.top + startArea.height == stageHeight - 2 ||
                startArea.height == MAX_ROOM_HEIGHT) {
            return false;
        }
        for(int col = startArea.left; col < startArea.left + startArea.width; col++) {
            if(placed[startArea.top + startArea.height + 1][col] != CellType.FREE) {
                return false;
            }
        }
        return true;
    }

    private void expandRight(RoomArea startArea) {
        startArea.width++;
    }

    private boolean canBeExpandRight(RoomArea startArea) {
        if(!expandProbTest() || startArea.left + startArea.width == stageWidth - 2 ||
                startArea.width == MAX_ROOM_WIDTH) {
            return false;
        }
        for(int line = startArea.top; line < startArea.top + startArea.height; line++) {
            if(placed[line][startArea.left + startArea.width + 1] != CellType.FREE) {
                return false;
            }
        }
        return true;
    }
    private boolean expandProbTest() {
        return RandomSource.getRandom().nextInt(100) < EXPAND_PROB;
    }
    private boolean placeProbTest() {
        return RandomSource.getRandom().nextInt(PLACE_PROB_TEST_MAX) < ROOM_PLACE_PROB;
    }

    private static class RoomArea {
        public int left;
        public int top;
        public int width;
        public int height;

        public RoomArea(int left, int top, int width, int height) {
            this.left = left;
            this.top = top;
            this.width = width;
            this.height = height;
        }
    }
    private static class Line {
        public int start;
        public int length;

        public Line(int start, int length) {
            this.start = start;
            this.length = length;
        }
    }
    private enum CellType {
        FREE, ROOM, WALL
    }
}
