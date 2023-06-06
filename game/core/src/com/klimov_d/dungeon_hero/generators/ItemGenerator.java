package com.klimov_d.dungeon_hero.generators;

import com.klimov_d.dungeon_hero.DifficultyFunctionArgs;
import com.klimov_d.dungeon_hero.items.*;
import com.klimov_d.dungeon_hero.service.RandomSource;

import java.util.Map;
import java.util.Random;
import java.util.function.Function;

public class ItemGenerator {
    private static final DifficultyFunctionArgs POINT_MODIFIERS_ARGS =
            new DifficultyFunctionArgs(2, 1, 1, 1);
    private static final DifficultyFunctionArgs TREASURE_COST_ARGS =
            new DifficultyFunctionArgs(5, 1, 5, 2);
    private static final int POINTS_EXIST_PROBABILITY = 30;
    private static final Class<?>[] types = new Class[]{
            Armor.class,
            Boots.class,
            Amulet.class,
            Helmet.class,
            Weapon.class,
            Trousers.class,
            Ring.class
    };
    private final Map<Class<?>, Function<Integer, Equipment>> generators;

    public ItemGenerator() {
        generators = Map.of(
                Armor.class, this::generateArmor,
                Boots.class, this::generateBoots,
                Amulet.class, this::generateAmulet,
                Helmet.class, this::generateHelmet,
                Ring.class, this::generateRing,
                Trousers.class, this::generateTrousers,
                Weapon.class, this::generateWeapon
        );
    }

    public Equipment generateEquipment(int difficulty) {
        int type = RandomSource.getRandom().nextInt(types.length);
        return generators.get(types[type]).apply(difficulty);
    }

    private void generateModifiers(Equipment equipment, int difficulty) {
        if (RandomSource.getRandom().nextInt(100) < POINTS_EXIST_PROBABILITY) {
            int stMod = POINT_MODIFIERS_ARGS.generateValue(RandomSource.getRandom(), difficulty);
            equipment.setStrengthModifier(stMod);
        }
        if (RandomSource.getRandom().nextInt(100) < POINTS_EXIST_PROBABILITY) {
            int hMod = POINT_MODIFIERS_ARGS.generateValue(RandomSource.getRandom(), difficulty);
            equipment.setHealthModifier(hMod);
        }
        if (RandomSource.getRandom().nextInt(100) < POINTS_EXIST_PROBABILITY) {
            int dMod = POINT_MODIFIERS_ARGS.generateValue(RandomSource.getRandom(), difficulty);
            equipment.setDamageModifier(dMod);
        }
        if (RandomSource.getRandom().nextInt(100) < POINTS_EXIST_PROBABILITY) {
            int defMod = POINT_MODIFIERS_ARGS.generateValue(RandomSource.getRandom(), difficulty);
            equipment.setDefenceModifier(defMod);
        }
        if (RandomSource.getRandom().nextInt(100) < POINTS_EXIST_PROBABILITY) {
            int attackMod = POINT_MODIFIERS_ARGS.generateValue(RandomSource.getRandom(), difficulty);
            equipment.setAttackModifier(attackMod);
        }
        if (RandomSource.getRandom().nextInt(100) < POINTS_EXIST_PROBABILITY) {
            int armorMod = POINT_MODIFIERS_ARGS.generateValue(RandomSource.getRandom(), difficulty);
            equipment.setArmorModifier(armorMod);
        }
    }

    public Armor generateArmor(int difficulty) {
        String name = generateArmorName();
        int weight = RandomSource.getRandom().nextInt(MIN_ARMOR_WEIGHT, MAX_ARMOR_WEIGHT);
        Armor armor = new Armor(weight, name);
        generateModifiers(armor, difficulty);
        return armor;
    }

    public Boots generateBoots(int difficulty) {
        String name = generateBootsName();
        int weight = RandomSource.getRandom().nextInt(MIN_BOOTS_WEIGHT, MAX_BOOTS_WEIGHT);
        Boots boots = new Boots(weight, name);
        generateModifiers(boots, difficulty);
        return boots;
    }

    public Helmet generateHelmet(int difficulty) {
        String name = generateHelmetName();
        int weight = RandomSource.getRandom().nextInt(MIN_HELMET_WEIGHT, MAX_HELMET_WEIGHT);
        Helmet helmet = new Helmet(weight, name);
        generateModifiers(helmet, difficulty);
        return helmet;
    }

    public Ring generateRing(int difficulty) {
        String name = generateRingName();
        int weight = RandomSource.getRandom().nextInt(MIN_RING_WEIGHT, MAX_RING_WEIGHT);
        Ring ring = new Ring(weight, name);
        generateModifiers(ring, difficulty);
        return ring;
    }

    public Weapon generateWeapon(int difficulty) {
        String name = generateWeaponName();
        int weight = RandomSource.getRandom().nextInt(MIN_WEAPON_WEIGHT, MAX_WEAPON_WEIGHT);
        Weapon weapon = new Weapon(weight, name);
        generateModifiers(weapon, difficulty);
        return weapon;
    }

    public Trousers generateTrousers(int difficulty) {
        String name = generateTrousersName();
        int weight = RandomSource.getRandom().nextInt(MIN_TROUSERS_WEIGHT, MAX_TROUSERS_WEIGHT);
        Trousers trousers = new Trousers(weight, name);
        generateModifiers(trousers, difficulty);
        return trousers;
    }

    public Amulet generateAmulet(int difficulty) {
        String name = generateAmuletName();
        int weight = RandomSource.getRandom().nextInt(MIN_AMULET_WEIGHT, MAX_AMULET_WEIGHT);
        Amulet amulet = new Amulet(weight, name);
        generateModifiers(amulet, difficulty);
        return amulet;
    }

    public Treasure generateTreasure(int difficulty) {
        String name = generateTreasureName();
        int cost = TREASURE_COST_ARGS.generateValue(RandomSource.getRandom(), difficulty);
        int weight = RandomSource.getRandom().nextInt(MIN_TREASURE_WEIGHT, MAX_TREASURE_WEIGHT);
        return new Treasure(weight, name, cost);
    }

    private static final String[] EQUIPMENT_SUFFIX = {
            "скорости",
            "силы",
            "ловкости",
            "души",
            "интелекта",
            "удачи",
            "богаства",
            "проклятья",
            "беды",
            "судьбы",
            "мастерства"
    };
    private static final String[] WEAPON_NAMES = {
            "Золотой меч",
            "Стальной меч",
            "Серебрянный меч",
            "Железный меч",
            "Деревянный лук",
            "Стальной лук",
            "Золотой лук",
            "Серебряный лук",
            "Золотой кинжал",
            "Стальной кинжал",
            "Серебрянный кинжал",
            "Железный кинжал",
            "Золотой топор",
            "Стальной топор",
            "Деревянный топор",
            "Железный топор",
            "Каменный молот",
            "Стальной молот",
            "Чугунный молот",
            "Железный молот"
    };
    private static final String[] HELMET_NAMES = {
            "Золотая каска",
            "Стальная каска",
            "Серебрянный каска",
            "Железная каска",
            "Медная каска",
            "Железный шлем",
            "Золотой тяжелый шлем",
            "Стальной легкий шлем",
            "Медный шлем",
            "Серебрянный шлем"
    };
    private static final String[] AMULET_NAMES = {
            "Золотой медальон",
            "Силовой медальон",
            "Удачливый медальон",
            "Интеллектуальный медальон",
            "Мастерский медальон",
            "Проклятый медальон",
            "Серебряный медальон",
            "Судьбоносный медальон",
            "Скоростной медальон",
            "Эпический медальон"
    };
    private static final String[] BOOTS_NAMES = {
            "Кожаные сапоги",
            "Позолоченные сапоги",
            "Железные сапоги",
            "Серебряные сапоги",
            "Кожаные башмаки",
            "Железные башмаки",
            "Золотые башмаки",
            "Железные сандали",
            "Кожаные сандали",
            "Серебряные сандали"
    };
    private static final String[] RING_NAMES = {
            "Золотое кольцо",
            "Изумрудное кольцо",
            "Бриллиантовое кольцо",
            "Медное кольцо",
            "Деревяное кольцо",
            "Мифическое кольцо",
            "Железное кольцо",
            "Бронзовое кольцо",
            "Чугунное кольцо",
            "Позолоченное кольцо",
            "Металлическое кольцо"
    };
    private static final String[] TROUSERS_NAMES = {
            "Поножи",
            "Металлические штаны",
            "Кожаные штаны",
            "Позолоченные штаны",
            "Серебряные штаны",
            "Стальные штаны",
            "Железные штаны",
            "Бронзовые штаны",
            "Медные штаны",
            "Чугунные штаны"
    };
    private static final String[] ARMOR_NAMES = {
            "Золотой нагрудник",
            "Стальной нагрудник",
            "Серебрянный нагрудник",
            "Железная нагрудник",
            "Железная мантия",
            "Золотая мантия",
            "Стальная мантия",
            "Серебрянный мантия"
    };
    private static final String[] TREASURE_PREFIX = {
            "Золотой",
            "Редкий",
            "Древний",
            "Эпический",
            "Мифический",
            "Легендарный",
            "Серебряный",
            "Алмазный",
    };
    private static final String[] TREASURE_NAMES = {
            "свиток",
            "манускрипт",
            "кубок",
            "фолиант",
            "том",
            "сундук",
            "жезл",
            "пояс",
            "грааль",
            "повязка"
    };
    private static final String[] TREASURE_SUFFIX = {
            "украшенный резьбой",
            "инкрустированный драгоценными камнями",
            "отделанный золотом",
            "украшенный вышивкой",
            "украшенный чеканкой",
            "украшенный металлом",
            "отделанный железом",
            "отделанный чугуном",
            "отделанный изумрудами",
            "украшенный серебром"
    };

    private String generateTreasureName() {
        String name = TREASURE_NAMES[RandomSource.getRandom().nextInt(TREASURE_NAMES.length)];
        String suffix = TREASURE_SUFFIX[RandomSource.getRandom().nextInt(TREASURE_SUFFIX.length)];
        String prefix = TREASURE_PREFIX[RandomSource.getRandom().nextInt(TREASURE_PREFIX.length)];
        return prefix + " " + name + " " + suffix;
    }

    private String generateBootsName() {
        String name = BOOTS_NAMES[RandomSource.getRandom().nextInt(BOOTS_NAMES.length)];
        String suffix = EQUIPMENT_SUFFIX[RandomSource.getRandom().nextInt(EQUIPMENT_SUFFIX.length)];
        return name + " " + suffix;
    }

    private String generateWeaponName() {
        String name = WEAPON_NAMES[RandomSource.getRandom().nextInt(WEAPON_NAMES.length)];
        String suffix = EQUIPMENT_SUFFIX[RandomSource.getRandom().nextInt(EQUIPMENT_SUFFIX.length)];
        return name + " " + suffix;
    }

    private String generateRingName() {
        String name = RING_NAMES[RandomSource.getRandom().nextInt(RING_NAMES.length)];
        String suffix = EQUIPMENT_SUFFIX[RandomSource.getRandom().nextInt(EQUIPMENT_SUFFIX.length)];
        return name + " " + suffix;
    }

    private String generateTrousersName() {
        String name = TROUSERS_NAMES[RandomSource.getRandom().nextInt(TROUSERS_NAMES.length)];
        String suffix = EQUIPMENT_SUFFIX[RandomSource.getRandom().nextInt(EQUIPMENT_SUFFIX.length)];
        return name + " " + suffix;
    }

    private String generateAmuletName() {
        String name = AMULET_NAMES[RandomSource.getRandom().nextInt(AMULET_NAMES.length)];
        String suffix = EQUIPMENT_SUFFIX[RandomSource.getRandom().nextInt(EQUIPMENT_SUFFIX.length)];
        return name + " " + suffix;
    }

    private String generateArmorName() {
        String name = ARMOR_NAMES[RandomSource.getRandom().nextInt(ARMOR_NAMES.length)];
        String suffix = EQUIPMENT_SUFFIX[RandomSource.getRandom().nextInt(EQUIPMENT_SUFFIX.length)];
        return name + " " + suffix;
    }

    private String generateHelmetName() {
        String name = HELMET_NAMES[RandomSource.getRandom().nextInt(HELMET_NAMES.length)];
        String suffix = EQUIPMENT_SUFFIX[RandomSource.getRandom().nextInt(EQUIPMENT_SUFFIX.length)];
        return name + " " + suffix;
    }

    private static final int MIN_WEAPON_WEIGHT = 3;
    private static final int MAX_WEAPON_WEIGHT = 8;
    private static final int MIN_BOOTS_WEIGHT = 4;
    private static final int MAX_BOOTS_WEIGHT = 9;
    private static final int MIN_RING_WEIGHT = 1;
    private static final int MAX_RING_WEIGHT = 3;
    private static final int MIN_TROUSERS_WEIGHT = 7;
    private static final int MAX_TROUSERS_WEIGHT = 15;
    private static final int MIN_HELMET_WEIGHT = 5;
    private static final int MAX_HELMET_WEIGHT = 10;
    private static final int MIN_ARMOR_WEIGHT = 10;
    private static final int MAX_ARMOR_WEIGHT = 25;
    private static final int MIN_AMULET_WEIGHT = 1;
    private static final int MAX_AMULET_WEIGHT = 4;
    private static final int MIN_TREASURE_WEIGHT = 1;
    private static final int MAX_TREASURE_WEIGHT = 10;
}
