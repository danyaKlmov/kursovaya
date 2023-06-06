package com.klimov_d.dungeon_hero.generators;

import com.klimov_d.dungeon_hero.*;
import com.klimov_d.dungeon_hero.Character;
import com.klimov_d.dungeon_hero.items.Equipment;
import com.klimov_d.dungeon_hero.items.Treasure;
import com.klimov_d.dungeon_hero.service.RandomSource;

import java.util.ArrayList;

public class DungeonGenerator {
    private static final int MAX_STAGE_COUNT = 25;
    private static final int MIN_STAGE_COUNT = 5;
    private static final int MIN_TREASURE_COUNT = 3;
    private static final int MAX_TREASURE_COUNT = 10;
    private static final int MONSTER_COUNT_DISPERSION = 2;
    private static final int MONSTER_COUNT_ADDITION = 1;
    private static final int MIN_EQUIPMENTS_COUNT = 3;
    private static final int MAX_EQUIPMENTS_COUNT = 10;
    public static final int PERSON_START_LEVEL = 3;
    private ItemGenerator itemGenerator;
    private CharacterAttributesGenerator attributesGenerator;
    private MonsterGenerator monsterGenerator;
    private CharacterGenerator characterGenerator;

    public DungeonGenerator() {
        this.itemGenerator = new ItemGenerator();
        this.attributesGenerator = new CharacterAttributesGenerator();
        this.characterGenerator = new CharacterGenerator(itemGenerator, attributesGenerator);
        this.monsterGenerator = new MonsterGenerator(itemGenerator, attributesGenerator);
    }

    public Dungeon generateDungeon() {
        ArrayList<Stage> stages= new ArrayList<>();
        int stagesCount = RandomSource.getRandom().nextInt(MIN_STAGE_COUNT, MAX_STAGE_COUNT);
        Dungeon dungeon = new Dungeon();
        for(int i = 0; i < stagesCount; i++) {
            StageGenerator stageGenerator = new StageGenerator(i + 1);
             Stage stage = stageGenerator.generate();
             dungeon.addStage(stage);
             stages.add(stage);
        }
        for(int i = 0; i < stages.size(); i++) {
            fillStage(i, stages);
        }
        generatePerson(dungeon);
        return dungeon;
    }

    private void generatePerson(Dungeon dungeon) {
        Character person = characterGenerator.generate(PERSON_START_LEVEL);
        person.setPosition(dungeon.getCurrentStage().getFreePosition());
        dungeon.setPerson(person);
    }

    private void fillStage(int index, ArrayList<Stage> stages) {
        placeTeleports(index, stages);
        placeMonsters(stages.get(index));
        placeTreasures(stages.get(index));
        placeFreeEquipments(stages.get(index));
    }

    private void placeMonsters(Stage stage) {
        int monsterCount = stage.getRoomsCount() +
                           MONSTER_COUNT_ADDITION +
                           RandomSource.getRandom().nextInt(-MONSTER_COUNT_DISPERSION, MONSTER_COUNT_DISPERSION + 1);
        for(int i = 0; i < monsterCount; i++) {
            placeMonster(stage);
        }
    }

    private void placeMonster(Stage stage) {
        Position pos = stage.getFreePosition();
        Monster monster = monsterGenerator.generateAsMonster(stage.getDifficulty());
        monster.setPosition(pos);
        stage.addMonster(monster);
    }

    private void placeTreasures(Stage stage) {
        int treasureCount = RandomSource.getRandom().nextInt(MIN_TREASURE_COUNT, MAX_TREASURE_COUNT);
        for(int i = 0; i < treasureCount; i++) {
            placeTreasure(stage);
        }
    }
    private void placeFreeEquipments(Stage stage) {
        int equipmentsCount = RandomSource.getRandom().nextInt(MIN_EQUIPMENTS_COUNT, MAX_EQUIPMENTS_COUNT);
        for(int i = 0; i < equipmentsCount; i++) {
            placeFreeEquipment(stage);
        }
    }

    private void placeFreeEquipment(Stage stage) {
        Position pos = stage.getFreePosition();
        Equipment equipment = itemGenerator.generateEquipment(stage.getDifficulty());
        equipment.setPosition(pos);
        stage.addEquipment(equipment);
    }

    private void placeTreasure(Stage stage) {
        Position pos = stage.getFreePosition();
        Treasure treasure = itemGenerator.generateTreasure(stage.getDifficulty());
        treasure.setPosition(pos);
        stage.addTreasure(treasure);
    }

    private void placeTeleports(int index, ArrayList<Stage> stages) {
        if(index > 0) {
            placeTeleportTo(stages.get(index), stages.get(index - 1));
        }
        if(index < stages.size() - 1) {
            placeTeleportTo(stages.get(index), stages.get(index + 1));
        }
    }

    private void placeTeleportTo(Stage fromStage, Stage toStage) {
        Position pos = fromStage.getFreePosition();
        Teleport teleport = new Teleport(pos);
        teleport.setToStage(toStage);
        fromStage.addTeleport(teleport);
    }
}
