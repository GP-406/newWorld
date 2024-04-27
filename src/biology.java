import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class biology {
    public static void main(String[] args) {
        World world = new World();
        int numIterations = 10; // Adjust to change runtime of main loop

        // Main Loop
        for (int i = 0; i < numIterations; i++) {
            world.createCreature();
            world.spawnResources();
            world.update();
            System.out.println("Round: " + (i + 1));
            System.out.println("Number of creatures in the world: " + world.getCreatures().size());
            System.out.println("Resources available: " + world.getResources());
            System.out.println();
        }
    }
}

class Creature {
    private String name;
    public int reproductionChance, deathChance;

    public Creature(String name, double reproductionChance, double deathChance) {
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


class World {
    private List<Creature> creatures;
    private int resources;

    public World() {
        this.creatures = new ArrayList<>();
        this.resources = 100; // Adjust to change starting resources
    }
    
    public void createCreature() {
        List<String> nameList = readNamesFromFile("name.txt");

        if (nameList.isEmpty()) {
            System.out.println("No names found in the name.txt file.");
            return;
        }

        String randomName = nameList.get(new Random().nextInt(nameList.size()));

        int reproductionChance = (int) (Math.random() * 10) + 1;
        int deathChance = (int) (Math.random() * 10) + 1;

        Creature newCreature = new Creature(randomName, reproductionChance, deathChance);
        creatures.add(newCreature);
    }

    public void spawnResources() {
        resources += new Random().nextInt(20); // Adjust number to change potential resource range.
    }

    public void update() {
        for (Creature creature : creatures) {
            if ((int) (Math.random() * 11) + 1 < creature.deathChance) {
                creature.die();
                creatures.remove(creature);
                break;
            }
            if ((int) (Math.random() * 11) + 1 < creature.reproductionChance) {
                Creature newCreature = creature.reproduce();
                creatures.add(newCreature);
                break;
            }
        }
    }

    public List<Creature> getCreatures() {
        return creatures;
    }

    public int getResources() {
        return resources;
    }

    private List<String> readNamesFromFile(String filepath) {
        List<String> nameList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] names = line.split(",");
                for (String name : names) {
                    String trimmedName = name.trim().replaceAll("\"", "");
                    if (!trimmedName.isEmpty()) {
                        nameList.add(trimmedName);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return nameList;
    }
}


