package com.klimov_d.dungeon_hero.items;

public abstract class Equipment extends Item {
    private int armorModifier = 0;
    private int healthModifier = 0;
    private int attackModifier = 0;
    private int defenceModifier = 0;
    private int strengthModifier = 0;
    private int damageModifier = 0;

    public Equipment(
        int weight,
        String name,
        int armorModifier,
        int healthModifier,
        int attackModifier,
        int defenceModifier,
        int strengthModifier,
        int damageModifier
    ) {
        super(weight, name);
        this.armorModifier = armorModifier;
        this.healthModifier = healthModifier;
        this.attackModifier = attackModifier;
        this.defenceModifier = defenceModifier;
        this.strengthModifier = strengthModifier;
        this.damageModifier = damageModifier;
    }

    public Equipment(int weight, String name) {
        super(weight, name);
    }

    public int getArmorModifier() {
        return armorModifier;
    }

    public int getHealthModifier() {
        return healthModifier;
    }

    public int getAttackModifier() {
        return attackModifier;
    }

    public int getDefenceModifier() {
        return defenceModifier;
    }

    public int getStrengthModifier() {
        return strengthModifier;
    }

    public int getDamageModifier() {
        return damageModifier;
    }

    public void setArmorModifier(int armorModifier) {
        this.armorModifier = armorModifier;
    }

    public void setHealthModifier(int healthModifier) {
        this.healthModifier = healthModifier;
    }

    public void setAttackModifier(int attackModifier) {
        this.attackModifier = attackModifier;
    }

    public void setDefenceModifier(int defenceModifier) {
        this.defenceModifier = defenceModifier;
    }

    public void setStrengthModifier(int strengthModifier) {
        this.strengthModifier = strengthModifier;
    }

    public void setDamageModifier(int damageModifier) {
        this.damageModifier = damageModifier;
    }

    @Override
    public String toString() {
        return String.format(
                "%s AT:%d AR:%d DA:%d DE:%d HE:%d ST:%d",
                super.toString(),
                attackModifier,
                armorModifier,
                damageModifier,
                defenceModifier,
                healthModifier,
                strengthModifier
        );
    }
}

