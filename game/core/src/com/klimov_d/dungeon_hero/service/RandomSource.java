package com.klimov_d.dungeon_hero.service;

import java.util.Random;

public class RandomSource {
    private static final Random random = new Random();
    public static Random getRandom() {
        return random;
    }
}
