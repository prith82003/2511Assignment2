package dungeonmania.entities.buildables;

import dungeonmania.entities.inventory.Inventory;

public abstract class BuildableRecipe {
    private IBuildMaterial materials;

    protected BuildableRecipe(IBuildMaterial materials) {
        this.materials = materials;
    }

    public boolean canConstruct(Inventory inventory, boolean remove) {
        return materials.canBuild(inventory, remove);
    }
}
