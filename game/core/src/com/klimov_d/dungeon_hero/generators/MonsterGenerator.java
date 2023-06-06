package com.klimov_d.dungeon_hero.generators;

import com.klimov_d.dungeon_hero.Character;
import com.klimov_d.dungeon_hero.Monster;
import com.klimov_d.dungeon_hero.service.RandomSource;

public class MonsterGenerator extends CharacterGenerator {

    private static final int EASY_DIFFICULTY_VALUE = 5;
    private static final int COMMON_DIFFICULTY_VALUE = 15;
    private static final int HARD_DIFFICULTY_VALUE = 25;
    private static final int MIN_DISTANCE_OF_SENS = 10;
    private static final int MAX_DISTANCE_OF_SENS = 25;

    private static final String[] EASY_MONSTER_PREFIX = {
            "Маленький",
            "Слабый",
            "Раненный",
            "Старый",
            "Карликовый",
            "Больной"
    };
    private static final String[] COMMON_MONSTER_PREFIX = {
            "Бодрый",
            "Обыкновенный",
            "Молодой",
            "Здоровый"
    };
    private static final String[] HARD_MONSTER_PREFIX = {
            "Крупный",
            "Взрослый",
            "Сильный",
            "Вожак",
            "Быстрый",
            "Матерый"
    };
    private static final String[] LEGENDARY_MONSTER_PREFIX = {
            "Огромный",
            "Великий",
            "Гигантский",
            "Мутировавший",
            "Легендарный",
            "Мифический",
            "Эпический"
    };
    private static final String[] MONSTER_TYPES = {
            "виверна",
            "орк",
            "огр",
            "тролль",
            "демон",
            "бандит",
            "паук",
            "дракон",
            "циклоп",
            "призрак",
            "ящер",
            "скелет"
    };
    private static final String[] MONSTER_MIDDLE_NAMES = {
            "лесной",
            "пещерный",
            "болотный",
            "холмистый",
            "горный",
            "теневой",
            "снежный",
            "огненный",
            "ледяной",
            "пустынный",
            "",
            "",
            "",
            ""
    };

    public MonsterGenerator(ItemGenerator itemGenerator, CharacterAttributesGenerator attributesGenerator) {
        super(itemGenerator, attributesGenerator);
    }

    @Override
    protected Character createCharacter(int difficulty) {
        String name = generateName(difficulty);
        int distanceOfSens = generateDistanceOfSens();
        int attack = attributesGenerator.generateAttack(difficulty);
        int armor = attributesGenerator.generateArmor(difficulty);
        int damage = attributesGenerator.generateDamage(difficulty);
        int defence = attributesGenerator.generateDefence(difficulty);
        int health = attributesGenerator.generateHealth(difficulty);
        int strength = attributesGenerator.generateStrength(difficulty);
        Monster monster = new Monster(attack, armor, damage, defence, health, strength, name, distanceOfSens);
        return monster;
    }

    public Monster generateAsMonster(int level) {
        return (Monster) generate(level);
    }

    private int generateDistanceOfSens() {
        return RandomSource.getRandom().nextInt(MIN_DISTANCE_OF_SENS, MAX_DISTANCE_OF_SENS);
    }

    private String generateName(int difficulty) {
        String prefix, middle, name;
        middle = MONSTER_MIDDLE_NAMES[RandomSource.getRandom().nextInt(MONSTER_MIDDLE_NAMES.length)];
        name = MONSTER_TYPES[RandomSource.getRandom().nextInt(MONSTER_TYPES.length)];
        if (difficulty <= EASY_DIFFICULTY_VALUE) {
            prefix = EASY_MONSTER_PREFIX[RandomSource.getRandom().nextInt(EASY_MONSTER_PREFIX.length)];
        } else if (difficulty <= COMMON_DIFFICULTY_VALUE) {
            prefix = COMMON_MONSTER_PREFIX[RandomSource.getRandom().nextInt(COMMON_MONSTER_PREFIX.length)];
        } else if (difficulty <= HARD_DIFFICULTY_VALUE) {
            prefix = HARD_MONSTER_PREFIX[RandomSource.getRandom().nextInt(HARD_MONSTER_PREFIX.length)];
        } else {
            prefix = LEGENDARY_MONSTER_PREFIX[RandomSource.getRandom().nextInt(LEGENDARY_MONSTER_PREFIX.length)];
        }
        if (middle.isEmpty()) {
            return prefix + " " + name;
        }
        return prefix + " " + middle + " " + name;
    }
}
