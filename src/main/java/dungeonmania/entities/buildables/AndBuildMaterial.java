package dungeonmania.entities.buildables;

import dungeonmania.entities.inventory.Inventory;

public class AndBuildMaterial implements IBuildMaterial {
    private IBuildMaterial[] materials;

    public AndBuildMaterial(IBuildMaterial... materials) {
        this.materials = materials;
    }

    @Override
    public boolean canBuild(Inventory inventory, boolean remove) {
        for (IBuildMaterial material : materials) {
            if (!material.canBuild(inventory, remove))
                return false;
        }

        return true;
    }

    @Override
    public String toString() {
        String str = "";
        for (IBuildMaterial material : materials) {
            str += material.toString() + " && ";
        }
        return str;
    }
}
