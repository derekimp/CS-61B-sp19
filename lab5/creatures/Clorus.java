package creatures;

import huglife.*;

import java.awt.Color;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Map;
import java.util.Random;

public class Clorus extends Creature {
    /**
     * RGB color
     */
    private int r;
    private int g;
    private int b;

    /**
     * creates clorus with energy equal to E.
     */
    public Clorus (double e) {
        super("clorus");
        r = 0;
        g = 0;
        b = 0;
        energy = e;
    }

    public Color color() {
        r = 34;
        g = 0;
        b = 231;
        return color(r, g, b);
    }

    public void move() {
        energy -= 0.03;
    }

    public void stay() {
        energy -= 0.01;
    }

    public void attack(Creature p) {
        energy += p.energy();
    }

    public Clorus replicate() {
        Clorus offspring = new Clorus(energy/2);
        offspring.color(r, g, b);
        return offspring;
    }

    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        ArrayList<Direction> emptyNeighbors = new ArrayList<>();
        ArrayList<Direction> plipNeighbors = new ArrayList<>();

        int emptyCount = 0;
        int impassibleCount = 0;
        int plipCount = 0;

        for (Direction dir : neighbors.keySet()) {
            if (neighbors.get(dir).name().equals("empty")) {
                emptyCount += 1;
                emptyNeighbors.add(dir);
            } else if (neighbors.get(dir).name().equals("impassible")) {
                impassibleCount += 1;
            } else if (neighbors.get(dir).name().equals("plip")) {
                plipCount += 1;
                plipNeighbors.add(dir);
            }
        }

        if (impassibleCount == 4) {
            return new Action(Action.ActionType.STAY);
        }

        if (plipCount >= 1) {
            Random generator = new Random();
            int randomIndex = generator.nextInt(plipNeighbors.size());
            return new Action(Action.ActionType.ATTACK, plipNeighbors.get(randomIndex));
        }

        if (energy >= 1) {
            if (emptyNeighbors.isEmpty()) {

            } else {
                Random generator = new Random();
                int randomIndex = generator.nextInt(emptyNeighbors.size());
                return new Action(Action.ActionType.REPLICATE, emptyNeighbors.get(randomIndex));
            }
        }

        if (emptyNeighbors.isEmpty()) {
            return new Action(Action.ActionType.STAY);
        } else {
            Random generator = new Random();
            int randomIndex = generator.nextInt(emptyNeighbors.size());
            return new Action(Action.ActionType.MOVE, emptyNeighbors.get(randomIndex));
        }
    }


}
