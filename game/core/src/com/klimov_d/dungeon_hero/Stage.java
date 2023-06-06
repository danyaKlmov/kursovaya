package com.klimov_d.dungeon_hero;

import com.klimov_d.dungeon_hero.items.Equipment;
import com.klimov_d.dungeon_hero.items.Item;
import com.klimov_d.dungeon_hero.items.Treasure;
import com.klimov_d.dungeon_hero.service.DrawManager;
import com.klimov_d.dungeon_hero.service.Drawable;
import com.klimov_d.dungeon_hero.service.RandomSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Stage implements Drawable {
    public static final char NULL_CELL_SYMBOL = '.';
    private int height;
    private int width;
    private ArrayList<ArrayList<Optional<Cell>>> cells;
    private final int difficulty;
    private final List<Monster> monsters;
    private final List<Treasure> treasures;
    private final List<Equipment> equipments;
    private final List<Teleport> teleports;
    private int roomsCount;

    public Stage(int height, int width, int difficulty) {
        this.teleports = new ArrayList<>();
        this.height = height;
        this.width = width;
        createCells();
        this.difficulty = difficulty;
        this.monsters = new ArrayList<>();
        this.treasures = new ArrayList<>();
        this.equipments = new ArrayList<>();
        this.roomsCount = 0;
    }

    private void createCells() {
        cells = new ArrayList<>();
        for(int i = 0; i < height; ++i) {
            cells.add(new ArrayList<>());
            for(int j = 0; j < width; ++j) {
                cells.get(i).add(Optional.empty());
            }
        }
    }

    public int getRoomsCount() { return roomsCount; }
    public void addTreasure(Treasure treasure) {
        treasures.add(treasure);
    }
    public void addEquipment(Equipment equipment) {
        equipments.add(equipment);
    }
    public void addMonster(Monster monster) {
        monsters.add(monster);
    }
    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
    public Optional<Cell> getCell(int line, int col) {
        return cells.get(line).get(col);
    }

    public int getDifficulty() {
        return difficulty;
    }

    public Optional<Cell> getCell(Position pos) {
        return cells.get(pos.getLine()).get(pos.getColumn());
    }
    public void addRoom(Position position, Position size) {
        for (int i = position.getLine(); i < size.getLine() + position.getLine(); i++) {
            for (int o = position.getColumn(); o < size.getColumn() + position.getColumn(); o++) {
                if (o == position.getColumn() ||
                   i == position.getLine() ||
                   o == size.getColumn() + position.getColumn() - 1 ||
                   i == size.getLine() + position.getLine() - 1) {
                    cells.get(i).set(o, Optional.of(new Cell(new Position(i, o), '#', false)));
                } else {
                    cells.get(i).set(o, Optional.of(new Cell(new Position(i, o), ' ', true)));
                }
            }
        }
        roomsCount++;
    }

    public void addTunnel(Position from, Position to, Orientation orientation) {
        if (orientation == Orientation.Vertical) {
            for (int i = from.getLine(); i <= to.getLine(); i++) {
                if(from.getColumn() > 0) {
                    if(getCell(i, from.getColumn() - 1).isEmpty())
                        cells.get(i).set(from.getColumn() - 1, Optional.of(new Cell(new Position(i, from.getColumn() - 1),
                                '#', false)));
                }
                cells.get(i).set(from.getColumn(), Optional.of(new Cell(new Position(i, from.getColumn()), ' ', true)));
                if(from.getColumn() < cells.get(i).size() - 1) {
                    if(getCell(i, from.getColumn() + 1).isEmpty())
                        cells.get(i).set(from.getColumn() + 1, Optional.of(new Cell(new Position(i, from.getColumn() + 1),
                                '#', false)));
                }
            }
        } else {
            for (int i = from.getColumn(); i <= to.getColumn(); i++) {
                if(from.getLine() > 0) {
                    if(getCell(from.getLine() - 1, i).isEmpty())
                        cells.get(from.getLine() - 1).set(i, Optional.of(new Cell(new Position(from.getLine() - 1, i),
                                '#', false)));
                }
                cells.get(from.getLine()).set(i, Optional.of(new Cell(new Position(from.getLine(), i), ' ', true)));
                if(from.getLine() < cells.size() - 1) {
                    if(getCell(from.getLine() + 1, i).isEmpty())
                        cells.get(from.getLine() + 1).set(i, Optional.of(new Cell(new Position(from.getLine() + 1, i),
                                '#', false)));
                }
            }
        }
    }
    public boolean positionIsFree(Position position) {
        return
            cellIsExist(position) &&
            noMonsters(position) &&
            noTeleports(position) &&
            isPassable(position);
    }

    public boolean isPassable(Position position) {
        if(getCell(position).isPresent())
            return getCell(position).get().isPassable();
        return false;
    }

    public boolean noTeleports(Position position) {
       return teleports.stream().noneMatch(t -> t.getPosition().equals(position));
    }

    public boolean noMonsters(Position position) {
        return monsters.stream().noneMatch(m -> m.getPosition().equals(position));
    }

    public boolean cellIsExist(Position position) {
        return getCell(position).isPresent();
    }

    public void addTeleport(Teleport teleport) {
        teleports.add(teleport);
    }

    @Override
    public void draw(DrawManager drawManager) {
        drawCells(drawManager);
        drawTeleports(drawManager);
        drawEquipments(drawManager);
        drawTreasures(drawManager);
        drawMonsters(drawManager);
    }

    private void drawMonsters(DrawManager drawManager) {
        for (var monster :
                monsters) {
            monster.draw(drawManager);
        }
    }
    private void drawTreasures(DrawManager drawManager) {
        for (var treasure :
                treasures) {
            treasure.draw(drawManager);
        }
    }
    private void drawEquipments(DrawManager drawManager) {
        for (var equipment :
                equipments) {
            equipment.draw(drawManager);
        }
    }
    private void drawTeleports(DrawManager drawManager) {
        for (var tp :
                teleports) {
            tp.draw(drawManager);
        }
    }
    private void drawCells(DrawManager drawManager) {
        for(int i = 0; i < height; ++i) {
            for(int j = 0; j < width; ++j) {
                if(getCell(i, j).isPresent())
                    getCell(i, j).get().draw(drawManager);
                else
                    drawManager.putSymbol(NULL_CELL_SYMBOL, new Position(i, j));
            }
        }
    }
    public Optional<Monster> getMonster(Position pos) {
        return monsters.stream().filter(m -> m.getPosition().equals(pos)).findAny();
    }
    public Optional<Teleport> getTeleport(Position pos) {
        return teleports.stream().filter(tp -> tp.getPosition().equals(pos)).findAny();
    }
    public List<Equipment> getEquipments(Position pos) {
        List<Equipment> items = new ArrayList<>();
        addEquipments(pos, items);
        return items;
    }
    public List<Treasure> getTreasures(Position pos) {
        List<Treasure> items = new ArrayList<>();
        addTreasures(pos, items);
        return items;
    }

    private void addTreasures(Position pos, List<Treasure> items) {
        items.addAll(treasures
                .stream()
                .filter(tr -> tr.getPosition().equals(pos))
                .toList()
        );
    }

    private void addEquipments(Position pos, List<Equipment> items) {
        items.addAll(equipments
                .stream()
                .filter(e -> e.getPosition().equals(pos))
                .toList()
        );
    }

    public void removeMonster(Monster monster) {
        monsters.remove(monster);
    }
    public void removeTreasure(Treasure treasure) {
        treasures.remove(treasure);
    }
    public void removeEquipment(Equipment equipment) {
        equipments.remove(equipment);
    }

    public List<Position> getNeighbours(Position position) {
        ArrayList<Position> neighbours = new ArrayList<>();
        if (position.getLine() > 0 &&
            getCell(new Position(position.getLine() - 1, position.getColumn())).isPresent()) {
            neighbours.add(new Position(position.getLine() - 1, position.getColumn()));
        }
        if (position.getColumn() > 0 &&
            getCell(new Position(position.getLine(), position.getColumn() - 1)).isPresent()) {
            neighbours.add(new Position(position.getLine(), position.getColumn() - 1));
        }
        if (position.getLine() < getHeight() - 1 &&
            getCell(new Position(position.getLine() + 1, position.getColumn())).isPresent()) {
            neighbours.add(new Position(position.getLine() + 1, position.getColumn()));
        }
        if (position.getColumn() < getWidth() - 1 &&
            getCell(new Position(position.getLine(), position.getColumn() + 1)).isPresent()) {
            neighbours.add(new Position(position.getLine(), position.getColumn() + 1));
        }
        return neighbours;
    }

    public void update(Dungeon dungeon) {
        for (var monster :
                monsters) {
            monster.action(dungeon);
        }
    }

    public Position getFreePosition() {
        int line = RandomSource.getRandom().nextInt(getHeight());
        int col = RandomSource.getRandom().nextInt(getWidth());
        while (!positionIsFree(new Position(line, col))) {
            col++;
            if(col == getWidth()) {
                col = 0;
                line++;
                if(line == getHeight()) {
                    line = 0;
                }
            }
        }
        return new Position(line, col);
    }

    public void cutEmptyCells() {
        int startLine = getStartLine();
        int startCol = getStartCol();
        int endLine = getEndLine();
        int endCol = getEndCol();
        ArrayList<ArrayList<Optional<Cell>>> newCells = new ArrayList<>();
        for(int i = startLine; i < endLine; ++i) {
            newCells.add(new ArrayList<>());
            for(int j = startCol; j < endCol; ++j) {
                newCells.get(i - startLine).add(buildNewCell(i, j, startLine, startCol));
            }
        }
        cells = newCells;
        height = cells.size();
        width = cells.get(0).size();
    }

    private Optional<Cell> buildNewCell(int oldLine, int oldCol, int startLine, int startCol) {
        Optional<Cell> cell = cells.get(oldLine).get(oldCol);
        cell.ifPresent(
                c -> c.setPosition(new Position(oldLine - startLine, oldCol - startCol))
        );
        return cell;
    }

    private int getEndCol() {
        for(int i = cells.get(0).size() - 1; i >= 0; --i) {
            if(columnNotEmpty(i))
                return i + 1;
        }
        return 0;
    }

    private boolean columnNotEmpty(int index) {
        for (int i = 0; i < cells.size(); ++i) {
            if(cells.get(i).get(index).isPresent())
                return true;
        }
        return false;
    }

    private int getEndLine() {
        for(int i = cells.size() - 1; i >= 0; --i) {
            if(lineNotEmpty(i))
                return i + 1;
        }
        return 0;
    }

    private int getStartCol() {
        for(int i = 0; i < cells.get(0).size(); ++i) {
            if(columnNotEmpty(i))
                return i;
        }
        return cells.size();
    }

    private int getStartLine() {
        for(int i = 0; i < cells.size(); ++i) {
            if(lineNotEmpty(i))
                return i;
        }
        return cells.size();
    }

    private boolean lineNotEmpty(int index) {
        for (int i = 0; i < cells.get(index).size(); ++i) {
            if(cells.get(index).get(i).isPresent())
                return true;
        }
        return false;
    }
}
