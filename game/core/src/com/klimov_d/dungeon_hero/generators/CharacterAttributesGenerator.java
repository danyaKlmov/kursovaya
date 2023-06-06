package com.klimov_d.dungeon_hero.generators;

import com.klimov_d.dungeon_hero.DifficultyFunctionArgs;
import com.klimov_d.dungeon_hero.service.RandomSource;

import java.util.Random;

public class CharacterAttributesGenerator {
    private static final DifficultyFunctionArgs ATTACK_ARGS = new DifficultyFunctionArgs(3,
            0.5f, 5, 1);
    private static final DifficultyFunctionArgs ARMOR_ARGS = new DifficultyFunctionArgs(1,
            0.2f, 0, 1);
    private static final DifficultyFunctionArgs DAMAGE_ARGS = new DifficultyFunctionArgs(1.5f,
            0.3f, 5, 1);
    private static final DifficultyFunctionArgs DEFENCE_ARGS = new DifficultyFunctionArgs(1f,
            0.3f, 1, 0.5f);
    private static final DifficultyFunctionArgs HEALTH_ARGS = new DifficultyFunctionArgs(3,
            0.5f, 10, 5);
    private static final DifficultyFunctionArgs STRENGTH_ARGS = new DifficultyFunctionArgs(3,
            0.5f, 2, 0.5f);

    public int generateAttack(int difficulty) {
        return ATTACK_ARGS.generateValue(RandomSource.getRandom(), difficulty);
    }
    public int generateArmor(int difficulty) {
        return ARMOR_ARGS.generateValue(RandomSource.getRandom(), difficulty);
    }

    public int generateDamage(int difficulty) {
        return DAMAGE_ARGS.generateValue(RandomSource.getRandom(), difficulty);
    }

    public int generateDefence(int difficulty) {
        return DEFENCE_ARGS.generateValue(RandomSource.getRandom(), difficulty);
    }

    public int generateHealth(int difficulty) {
        return HEALTH_ARGS.generateValue(RandomSource.getRandom(), difficulty);
    }

    public int generateStrength(int difficulty) {
        return STRENGTH_ARGS.generateValue(RandomSource.getRandom(), difficulty);

    }
}
