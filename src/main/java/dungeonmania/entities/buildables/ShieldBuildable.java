package dungeonmania.entities.buildables;

import dungeonmania.entities.collectables.Key;
import dungeonmania.entities.collectables.Treasure;
import dungeonmania.entities.collectables.Wood;
import dungeonmania.entities.inventory.Inventory;

public class ShieldBuildable extends BuildableRecipe {
    private static final int NUM_WOOD = 2;
    private static final int NUM_TREASURE = 1;

    public ShieldBuildable() {
        super(new AndBuildMaterial(new BuildMaterial(Wood.class, NUM_WOOD), new OrBuildMaterial(
                new BuildMaterial(Treasure.class, NUM_TREASURE), new BuildMaterial(Key.class, NUM_TREASURE))));

        Inventory.addBuildable("shield", this);
    }
}
