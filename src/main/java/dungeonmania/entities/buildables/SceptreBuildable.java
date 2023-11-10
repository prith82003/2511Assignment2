package dungeonmania.entities.buildables;

import dungeonmania.entities.collectables.Arrow;
import dungeonmania.entities.collectables.Wood;
import dungeonmania.entities.collectables.Key;
import dungeonmania.entities.collectables.SunStone;
import dungeonmania.entities.collectables.Treasure;

public class SceptreBuildable extends BuildableRecipe {
    private static final int NUM_WOOD = 1;
    private static final int NUM_ARROWS = 2;
    private static final int NUM_KEYS = 1;
    private static final int NUM_TREASURE = 1;
    private static final int NUM_SUNSTONE = 1;

    public SceptreBuildable() {
        super(new AndBuildMaterial(
                new OrBuildMaterial(new BuildMaterial(Wood.class, NUM_WOOD),
                        new BuildMaterial(Arrow.class, NUM_ARROWS)),
                new OrBuildMaterial(new BuildMaterial(Key.class, NUM_KEYS),
                        new BuildMaterial(Treasure.class, NUM_TREASURE)),
                new BuildMaterial(SunStone.class, NUM_SUNSTONE)));
    }
}
