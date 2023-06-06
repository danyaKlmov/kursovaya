package com.klimov_d.dungeon_hero;

import java.util.Random;

public class DifficultyFunctionArgs {
    private float coefficient;
    private float coefficientDispersion;
    private float addition;
    private float additionDispersion;

    public DifficultyFunctionArgs(float coefficient, float coefficientDispersion, float addition,
                                  float additionDispersion) {
        this.coefficient = coefficient;
        this.coefficientDispersion = coefficientDispersion;
        this.addition = addition;
        this.additionDispersion = additionDispersion;
    }

    public float getCoefficient() {
        return coefficient;
    }

    public float getCoefficientDispersion() {
        return coefficientDispersion;
    }

    public float getAddition() {
        return addition;
    }

    public float getAdditionDispersion() {
        return additionDispersion;
    }

    public int generateValue(Random random, int difficulty) {
        float coeff = random.nextFloat(getCoefficient() - getCoefficientDispersion(),
                getCoefficient() + getCoefficientDispersion());
        float addition = random.nextFloat(getAddition() - getAdditionDispersion(),
                getAddition() + getAdditionDispersion());
        int a = (int) (difficulty * coeff + addition);
        if (a < 0) {
            a = 0;
        }
        return a;
    }
}
