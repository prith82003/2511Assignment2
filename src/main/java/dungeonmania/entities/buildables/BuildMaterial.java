package dungeonmania.entities.buildables;

import dungeonmania.entities.collectables.Key;
import dungeonmania.entities.collectables.Treasure;
import dungeonmania.entities.collectables.SunStone;
import dungeonmania.entities.inventory.Inventory;
import dungeonmania.entities.inventory.InventoryItem;

public class BuildMaterial implements IBuildMaterial {
    private Class<? extends InventoryItem> item;
    private int amount;

    //// SUNSRTONE DOESNT REMOVE IN SOME CASES

    public BuildMaterial(Class<? extends InventoryItem> item, int amount) {
        this.item = item;
        this.amount = amount;
    }

    @Override
    public boolean canBuild(Inventory inventory, boolean remove) {

        if ((item.equals(Treasure.class) && !item.equals(SunStone.class))) {
            if (inventory.getEntities(item).size() >= amount) {
                if (remove) {
                    inventory.removeType(item, amount);
                }
                return true;
            } else {
                if (inventory.getEntities(SunStone.class).size() >= amount
                        && inventory.getEntities(Key.class).size() <= 0) {
                    return true;
                }
                return false;
            }
        }

        if (item.equals(Key.class)) {
            if (inventory.getEntities(item).size() >= amount) {
                if (remove) {
                    inventory.removeType(item, amount);
                }
                return true;
            } else {
                if (inventory.getEntities(SunStone.class).size() >= amount
                        && inventory.getEntities(Treasure.class).size() <= 0) {
                    return true;
                }
                return false;
            }
        }

        if (inventory.getEntities(item).size() >= amount) {
            if (remove) {
                inventory.removeType(item, amount);
            }
            return true;
        }

        return false;
    }

    @Override
    public String toString() {
        return amount + " x " + item.getName();
    }
}
