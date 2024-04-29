package Creatures;

public class Creature {
    private String name;
    public int reproductionChance, deathChance;

    public Creature(String name, int reproductionChance, int deathChance) {
        this.name = name;
        this.reproductionChance = reproductionChance;
        this.deathChance = deathChance;
    }

    public void die() {
        System.out.println(name + " has died.");
    }

    public Creature reproduce() {
        return new Creature(name,reproductionChance, deathChance);
    }
}
