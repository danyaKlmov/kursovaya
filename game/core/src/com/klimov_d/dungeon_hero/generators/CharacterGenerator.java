package com.klimov_d.dungeon_hero.generators;

import com.klimov_d.dungeon_hero.Character;
import com.klimov_d.dungeon_hero.Monster;
import com.klimov_d.dungeon_hero.service.RandomSource;

import java.util.Random;

public class CharacterGenerator {
    private static final int EQUIPMENT_EXIST_PROBABILITY = 30;

    protected ItemGenerator itemGenerator;
    protected CharacterAttributesGenerator attributesGenerator;

    public CharacterGenerator(ItemGenerator itemGenerator, CharacterAttributesGenerator attributesGenerator) {
        this.itemGenerator = itemGenerator;
        this.attributesGenerator = attributesGenerator;
    }

    public Character generate(int level) {
        Character character = createCharacter(level);
        equip(character, level);
        return character;
    }

    protected Character createCharacter(int level) {
        int attack = attributesGenerator.generateAttack(level);
        int armor = attributesGenerator.generateArmor(level);
        int damage = attributesGenerator.generateDamage(level);
        int defence = attributesGenerator.generateDefence(level);
        int health = attributesGenerator.generateHealth(level);
        int strength = attributesGenerator.generateStrength(level);
        Character character = new Character(attack, armor, damage, defence, health, strength);
        return character;
    }

    protected void equip(Character character, int level) {
        equipHelmet(character, level);
        equipArmor(character, level);
        equipAmulet(character, level);
        equipTrousers(character, level);
        equipBoots(character, level);
        equipRing(character, level);
        equipRing(character, level);
        equipWeapon(character, level);
        equipWeapon(character, level);
    }

    private void equipBoots(Character character, int level) {
        if (RandomSource.getRandom().nextInt(100) >= EQUIPMENT_EXIST_PROBABILITY) {
            return;
        }
        character.equipBoots(itemGenerator.generateBoots(level));
    }

    private void equipWeapon(Character character, int level) {
        if (RandomSource.getRandom().nextInt(100) >= EQUIPMENT_EXIST_PROBABILITY) {
            return;
        }
        character.equipWeapon(itemGenerator.generateWeapon(level));
    }

    private void equipRing(Character character, int level) {
        if (RandomSource.getRandom().nextInt(100) >= EQUIPMENT_EXIST_PROBABILITY) {
            return;
        }
        character.equipRing(itemGenerator.generateRing(level));
    }

    private void equipTrousers(Character character, int level) {
        if (RandomSource.getRandom().nextInt(100) >= EQUIPMENT_EXIST_PROBABILITY) {
            return;
        }
        character.equipTrousers(itemGenerator.generateTrousers(level));
    }

    private void equipAmulet(Character character, int level) {
        if (RandomSource.getRandom().nextInt(100) >= EQUIPMENT_EXIST_PROBABILITY) {
            return;
        }
        character.equipAmulet(itemGenerator.generateAmulet(level));
    }

    private void equipArmor(Character character, int level) {
        if (RandomSource.getRandom().nextInt(100) >= EQUIPMENT_EXIST_PROBABILITY) {
            return;
        }
        character.equipArmor(itemGenerator.generateArmor(level));
    }

    private void equipHelmet(Character character, int level) {
        if (RandomSource.getRandom().nextInt(100) >= EQUIPMENT_EXIST_PROBABILITY) {
            return;
        }
        character.equipHelmet(itemGenerator.generateHelmet(level));
    }
}
