package creatures;

import huglife.*;

import java.awt.Color;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Map;
import java.util.Random;

/**
 * An implementation of a motile pacifist photosynthesizer.
 *
 * @author Josh Hug
 */
public class Plip extends Creature {

    /**
     * red color.
     */
    private int r;
    /**
     * green color.
     */
    private int g;
    /**
     * blue color.
     */
    private int b;

    /**
     * creates plip with energy equal to E.
     */
    public Plip(double e) {
        super("plip");
        r = 0;
        g = 0;
        b = 0;
        energy = e;
    }

    /**
     * creates a plip with energy equal to 1.
     */
    public Plip() {
        this(1);
    }

    /**
     * Should return a color with red = 99, blue = 76, and green that varies
     * linearly based on the energy of the Plip. If the plip has zero energy,
     * it should have a green value of 63. If it has max energy, it should
     * have a green value of 255. The green value should vary with energy
     * linearly in between these two extremes. It's not absolutely vital
     * that you get this exactly correct.
     */
    public Color color() {
        r = 99;
        g = (int) (96*energy+63);
        b = 76;
        return color(r, g, b);
    }

    /**
     * sets energy to 2.0 if energy exists 2.0.
     */
    public void checkEnergy() {
        if (energy > 2.0) {
            energy = 2.0;
        }
        else if (energy < 0.0) {
            energy = 0.0;
        }
    }

    /**
     * Do nothing with C, Plips are pacifists.
     */
    public void attack(Creature c) {
        // do nothing.
    }

    /**
     * Plips should lose 0.15 units of energy when moving. If you want to
     * to avoid the magic number warning, you'll need to make a
     * private static final variable. This is not required for this lab.
     */
    public void move() {
        energy -= 0.15;
        checkEnergy();
    }


    /**
     * Plips gain 0.2 energy when staying due to photosynthesis.
     */
    public void stay() {
        energy += 0.2;
        checkEnergy();
    }

    /**
     * Plips and their offspring each get 50% of the energy, with none
     * lost to the process. Now that's efficiency! Returns a baby
     * Plip.
     */
    public Plip replicate() {
        Plip newPlip = new Plip(energy/2);
        newPlip.color(r, g, b);
        return newPlip;
    }

    /**
     * Plips take exactly the following actions based on NEIGHBORS:
     * 1. If no empty adjacent spaces, STAY.
     * 2. Otherwise, if energy >= 1, REPLICATE towards an empty direction
     * chosen at random.
     * 3. Otherwise, if any Cloruses, MOVE with 50% probability,
     * towards an empty direction chosen at random.
     * 4. Otherwise, if nothing else, STAY
     * <p>
     * Returns an object of type Action. See Action.java for the
     * scoop on how Actions work. See SampleCreature.chooseAction()
     * for an example to follow.
     */
    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        // Rule 1
        ArrayList<Direction> emptyNeighbors = new ArrayList<>();
        boolean anyClorus = false;
        // TODO
        // (Google: Enhanced for-loop over keys of NEIGHBORS?)
        // for () {...}
        int emptyCount = 0;
        int impassibleCount = 0;

        for (Direction dir : neighbors.keySet()) {
            if (neighbors.get(dir).name().equals("empty")) {
                emptyCount += 1;
                emptyNeighbors.add(dir);
            } else if (neighbors.get(dir).name().equals("impassible")) {
                impassibleCount += 1;
            } else if (neighbors.get(dir).name().equals("clorus")) {
                anyClorus = true;
            }
        }

        if (emptyCount == 0) {
            return new Action(Action.ActionType.STAY);
        }

        if (impassibleCount == 4) {
            return new Action(Action.ActionType.STAY);
        }

        // Rule 2
        // HINT: randomEntry(emptyNeighbors)
        if (energy >= 1.0) {
            Random generator = new Random();
            int randomIndex = generator.nextInt(emptyNeighbors.size());
            return new Action(Action.ActionType.REPLICATE, emptyNeighbors.get(randomIndex));
        }

        // Rule 3
        if (anyClorus) {
            Random generator = new Random();
            int randomIndex = generator.nextInt(1);
            if (randomIndex >0) {
                randomIndex = generator.nextInt(emptyNeighbors.size());
                return new Action(Action.ActionType.REPLICATE, emptyNeighbors.get(randomIndex));
            } else {
                return new Action(Action.ActionType.STAY);
            }
        }

        // Rule 4
        return new Action(Action.ActionType.STAY);
    }
}
