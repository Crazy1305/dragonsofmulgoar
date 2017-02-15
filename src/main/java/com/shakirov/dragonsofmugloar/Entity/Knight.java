package com.shakirov.dragonsofmugloar.Entity;

/**
 *
 * @author vadim.shakirov
 */
public class Knight {
    
    private final String name;
    private final int attack;
    private final int armor;
    private final int agility;
    private final int endurance;

    public Knight(String name, int attack, int armor, int agility, int endurance) {
        this.name = name;
        this.attack = attack;
        this.armor = armor;
        this.agility = agility;
        this.endurance = endurance;
    }
    
    public Knight() {
        name = "";
        attack = 0;
        armor = 0;
        agility = 0;
        endurance = 0;
    }
    
    public String getName() { return name; }
    public int getAttack() { return attack; }
    public int getArmor() { return armor; }
    public int getAgility() { return agility; }
    public int getEndurance() { return endurance; }
}
