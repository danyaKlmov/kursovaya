package com.klimov_d.dungeon_hero.items;

import com.klimov_d.dungeon_hero.Character;

public class Amulet extends Equipment {
    public Amulet(int weight, String name) {
        super(weight, name);
    }

    public Amulet(
        int weight,
        String name,
        int armorModifier,
        int healthModifier,
        int attackModifier,
        int defenceModifier,
        int strengthModifier,
        int damageModifier
    ) {
        super(
            weight,
            name,
            armorModifier,
            healthModifier,
            attackModifier,
            defenceModifier,
            strengthModifier,
            damageModifier
        );
    }

    @Override
    public void use(Character character) {
        character.removeAmulet();
        character.equipAmulet(this);
    }
}
