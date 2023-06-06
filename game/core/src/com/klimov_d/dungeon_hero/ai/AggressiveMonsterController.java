package com.klimov_d.dungeon_hero.ai;

import com.klimov_d.dungeon_hero.*;
import com.klimov_d.dungeon_hero.Character;
import com.klimov_d.dungeon_hero.service.RandomSource;

public class AggressiveMonsterController implements MonsterController {
    private final static int DEFAULT_DISTANCE_OF_SENS = 10;
    private PathFinder pathFinder;
    private int distanceOfSens;

    public AggressiveMonsterController() {
        this.distanceOfSens = DEFAULT_DISTANCE_OF_SENS;
        this.pathFinder = new WavePathFinder();
    }
    public AggressiveMonsterController(int distanceOfSens) {
        this.distanceOfSens = distanceOfSens;
        this.pathFinder = new WavePathFinder();
    }

    @Override
    public void action(Dungeon dungeon, Monster monster) {
        Path pathToPerson = pathFinder.findPath(
                dungeon.getCurrentStage(),
                monster.getPosition(),
                dungeon.getPerson().getPosition()
        );

        if(pathToPerson == null || pathToPerson.length() > distanceOfSens)
            randomMove(monster, dungeon.getCurrentStage());
        else if(pathToPerson.length() == 2)
            attackPerson(monster, dungeon.getPerson());
        else
            moveToPerson(monster, pathToPerson);
    }

    private void moveToPerson(Monster monster, Path pathToPerson) {
        monster.setPosition(pathToPerson.getNextPoint());
    }

    private void randomMove(Monster monster, Stage stage) {
        var neighbors = stage.getNeighbours(monster.getPosition());
        int index = RandomSource.getRandom().nextInt(neighbors.size());
        if(stage.positionIsFree(neighbors.get(index)))
            monster.setPosition(neighbors.get(index));
    }

    private void attackPerson(Monster monster, Character person) {
        DungeonHeroGame.instance().getDrawManager().addMessage(
                String.format("%s атакует персонажа", monster.toString())
        );
        monster.attack(person);
        if(person.getHealth() <= 0)
            DungeonHeroGame.instance().getDrawManager().addMessage(
                    "Герой убит " + this
            );
    }
}
