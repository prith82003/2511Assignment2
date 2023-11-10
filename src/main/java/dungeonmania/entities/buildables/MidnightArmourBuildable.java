package dungeonmania.entities.buildables;

import dungeonmania.entities.collectables.SunStone;
import dungeonmania.entities.collectables.Sword;

public class MidnightArmourBuildable extends BuildableRecipe {
    private static final int NUM_SWORDS = 1;
    private static final int NUM_SUNSTONE = 1;

    public MidnightArmourBuildable() {
        super(new AndBuildMaterial(new BuildMaterial(Sword.class, NUM_SWORDS),
                new BuildMaterial(SunStone.class, NUM_SUNSTONE)));
    }
}
