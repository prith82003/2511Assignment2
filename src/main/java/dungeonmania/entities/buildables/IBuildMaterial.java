package dungeonmania.entities.buildables;

import dungeonmania.entities.inventory.Inventory;

public interface IBuildMaterial {
    public abstract boolean canBuild(Inventory inventory, boolean remove);
}
