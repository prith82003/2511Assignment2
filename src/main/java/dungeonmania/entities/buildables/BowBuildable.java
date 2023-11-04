package dungeonmania.entities.buildables;

import dungeonmania.entities.collectables.Arrow;
import dungeonmania.entities.collectables.Wood;

public class BowBuildable extends BuildableRecipe {
    private static final int NUM_WOOD = 1;
    private static final int NUM_ARROWS = 3;

    public BowBuildable() {
        super(new AndBuildMaterial(new BuildMaterial(Wood.class, NUM_WOOD),
                new BuildMaterial(Arrow.class, NUM_ARROWS)));
    }
}
