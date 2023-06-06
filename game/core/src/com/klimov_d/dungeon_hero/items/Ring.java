package com.klimov_d.dungeon_hero.items;

import com.klimov_d.dungeon_hero.Character;

public class Ring extends Equipment {
    public Ring(int weight, String name) {
        super(weight, name);
    }

    public Ring(
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
        if(!character.containsFreeRingsSlots()) {
            character.removeRing(0);
        }
        character.equipRing(this);
    }
}
