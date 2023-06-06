package com.klimov_d.dungeon_hero;

import com.klimov_d.dungeon_hero.items.*;
import com.klimov_d.dungeon_hero.service.DrawManager;
import com.klimov_d.dungeon_hero.service.Drawable;
import com.klimov_d.dungeon_hero.service.RandomSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Character implements Drawable {
    private static final char SYMBOL = '@';
    public static final int CHANCE_BOUND = 100;
    public static final int MIN_DAMAGE = 1;
    public static final int MIN_HIT_CHANCE = 5;
    public static final int HIT_CHANCE_MULTIPLIER = 3;
    private Position position;
    private Helmet helmetSlot;
    private Amulet amuletSlot;
    private Armor armorSlot;
    private Boots bootsSlot;
    private Ring[] ringSlots;
    private Weapon[] weaponSlots;
    private Trousers trousersSlot;
    private static final int countOfRings = 2;
    private static final int countOfWeapons = 2;
    private List<Treasure> inventory;
    private int attack;
    private int armor;
    private int damage;
    private int defence;
    private int maxHealth;
    private int currentHealth;
    private int strength;
    private int weightInventory;

    public Character(
            int attack,
            int armor,
            int damage,
            int defence,
            int health,
            int strength
    ) {
        this.attack = attack;
        this.armor = armor;
        this.damage = damage;
        this.defence = defence;
        this.maxHealth = health;
        this.currentHealth = health;
        this.strength = strength;
        this.inventory = new ArrayList<>();
        this.ringSlots = new Ring[countOfRings];
        this.weaponSlots = new Weapon[countOfWeapons];
        this.position = new Position();
    }

    public int getAttack() {
        return attack;
    }

    public int getArmor() {
        return armor;
    }

    public int getDamage() {
        return damage;
    }

    public int getDefence() {
        return defence;
    }

    public int getHealth() {
        return currentHealth;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getStrength() {
        return strength;
    }

    public void addTreasure(Treasure item) {
        inventory.add(item);
        weightInventory += item.getWeight();
    }

    public void removeTreasure(Treasure item) {
        inventory.remove(item);
        weightInventory -= item.getWeight();
    }

    public int getInventoryWeight() {
        return weightInventory;
    }

    public void equipHelmet(Helmet helmet) {
        if (helmetSlot != null) {
            return;
        }
        applyModifiersFrom(helmet);
        helmetSlot = helmet;
    }

    public void removeHelmet() {
        if (helmetSlot != null) {
            removeModifiersFrom(helmetSlot);
            helmetSlot = null;
        }
    }

    public void equipTrousers(Trousers trousers) {
        if (trousersSlot != null) {
            return;
        }
        applyModifiersFrom(trousers);
        trousersSlot = trousers;
    }

    public void removeTrousers() {
        if (trousersSlot != null) {
            removeModifiersFrom(trousersSlot);
            trousersSlot = null;
        }
    }

    public void equipAmulet(Amulet amulet) {
        if (amuletSlot != null) {
            return;
        }
        applyModifiersFrom(amulet);
        amuletSlot = amulet;
    }

    public void removeAmulet() {
        if (amuletSlot != null) {
            removeModifiersFrom(amuletSlot);
            amuletSlot = null;
        }
    }

    public void equipArmor(Armor armor) {
        if (armorSlot != null) {
            return;
        }
        applyModifiersFrom(armor);
        armorSlot = armor;
    }

    public void removeArmor() {
        if (armorSlot != null) {
            removeModifiersFrom(armorSlot);
            armorSlot = null;
        }
    }

    public void equipBoots(Boots boots) {
        if (bootsSlot != null) {
            return;
        }
        applyModifiersFrom(boots);
        bootsSlot = boots;
    }

    public void removeBoots() {
        if (bootsSlot != null) {
            removeModifiersFrom(bootsSlot);
            bootsSlot = null;
        }
    }

    public void equipRing(Ring ring) {
        for (int i = 0; i < ringSlots.length; i++) {
            if (ringSlots[i] == null) {
                applyModifiersFrom(ring);
                ringSlots[i] = ring;
                break;
            }
        }
    }

    public void removeRing(int index) {
        if (ringSlots[index] != null) {
            removeModifiersFrom(ringSlots[index]);
            ringSlots[index] = null;
        }
    }

    public void equipWeapon(Weapon weapon) {
        for (int i = 0; i < weaponSlots.length; i++) {
            if (weaponSlots[i] == null) {
                applyModifiersFrom(weapon);
                weaponSlots[i] = weapon;
                break;
            }
        }
    }

    public void removeWeapon(int index) {
        if (weaponSlots[index] != null) {
            removeModifiersFrom(weaponSlots[index]);
            weaponSlots[index] = null;
        }
    }

    public void applyModifiersFrom(Equipment equipment) {
        attack += equipment.getAttackModifier();
        damage += equipment.getDamageModifier();
        armor += equipment.getArmorModifier();
        defence += equipment.getDefenceModifier();
        currentHealth += equipment.getHealthModifier();
        maxHealth += equipment.getHealthModifier();
        strength += equipment.getStrengthModifier();
    }

    public void removeModifiersFrom(Equipment equipment) {
        attack -= equipment.getAttackModifier();
        damage -= equipment.getDamageModifier();
        armor -= equipment.getArmorModifier();
        defence -= equipment.getDefenceModifier();
        strength -= equipment.getStrengthModifier();
        maxHealth -= equipment.getHealthModifier();
        if (currentHealth > maxHealth) {
            currentHealth = maxHealth;
        }
    }
    public boolean containsFreeRingsSlots() {
        for(int i = 0; i < ringSlots.length; i++) {
            if (ringSlots[i] == null) {
                return true;
            }
        }
        return false;
    }
    public boolean containsFreeWeaponSlots() {
        for(int i = 0; i < weaponSlots.length; i++) {
            if (weaponSlots[i] == null) {
                return true;
            }
        }
        return false;
    }

    public Optional<Helmet> getHelmetSlot() {
        if(helmetSlot != null)
            return Optional.of(helmetSlot);
        return Optional.empty();
    }
    public Optional<Amulet> getAmuletSlot() {
        if(amuletSlot != null)
            return Optional.of(amuletSlot);
        return Optional.empty();
    }
    public Optional<Armor> getArmorSlot() {
        if(armorSlot != null)
            return Optional.of(armorSlot);
        return Optional.empty();
    }
    public Optional<Boots> getBootsSlot() {
        if(bootsSlot != null)
            return Optional.of(bootsSlot);
        return Optional.empty();
    }
    public Optional<Ring> getRingSlot(int number) {
        if(number < 0 || number >= countOfRings) {
            return Optional.empty();
        }
        if(ringSlots[number] != null)
            return Optional.of(ringSlots[number]);
        return Optional.empty();
    }
    public Optional<Weapon> getWeaponSlot(int number) {
        if(number < 0 || number >= countOfWeapons) {
            return Optional.empty();
        }
        if(weaponSlots[number] != null)
            return Optional.of(weaponSlots[number]);
        return Optional.empty();
    }
    public Optional<Trousers> getTrousersSlot() {
        if(trousersSlot != null)
            return Optional.of(trousersSlot);
        return Optional.empty();
    }

    public void useItem(Item item) {
        item.use(this);
    }

    public List<Item> getInventory() {
        return new ArrayList<>(inventory);
    }

    public void setPosition(Position pos) {
        position = pos;
    }

    public Position getPosition() {
        return position;
    }

    @Override
    public void draw(DrawManager drawManager) {
        drawManager.putSymbol(SYMBOL, position);
    }

    public void attack(Character target) {
        int hitChance = calcHitChance(target);
        if(RandomSource.getRandom().nextInt(CHANCE_BOUND) < hitChance) {
            int damage = calcDamage(target);
            target.takeDamage(damage);
            DungeonHeroGame.instance().getDrawManager().addMessage(
                    String.format("Попал и нанес очков %d урона", damage)
            );
        } else {
            DungeonHeroGame.instance().getDrawManager().addMessage(
                    "Промах"
            );
        }
    }

    private int calcHitChance(Character target) {
        int atDefDifference = attack - target.defence;
        if(atDefDifference < MIN_HIT_CHANCE)
            atDefDifference = MIN_HIT_CHANCE;
        return HIT_CHANCE_MULTIPLIER * atDefDifference;
    }

    private int calcDamage(Character target) {
        int damage = this.damage - target.armor;
        if(damage <= MIN_DAMAGE)
            damage = MIN_DAMAGE;
        return damage;
    }

    private void takeDamage(int damage) {
        currentHealth -= damage;
    }

    @Override
    public String toString() {
        return String.format(
                "AT:%d AR:%d DA:%d DE:%d HE:%d ST:%d",
                attack,
                armor,
                damage,
                defence,
                currentHealth,
                strength
        );
    }

    public void resetHp() {
        currentHealth = maxHealth;
    }

    public int getTreasuresCost() {
        int sum = 0;
        for (var treasure :
                inventory) {
            sum += treasure.getCost();
        }
        return sum;
    }
}
