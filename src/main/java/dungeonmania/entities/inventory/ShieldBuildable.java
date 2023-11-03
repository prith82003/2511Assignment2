package dungeonmania.entities.inventory;

import java.util.List;

import dungeonmania.entities.collectables.Key;
import dungeonmania.entities.collectables.Treasure;
import dungeonmania.entities.collectables.Wood;

public class ShieldBuildable implements IBuildable {
    private static final int NUM_WOOD = 2;
    private static final int NUM_TREASURE = 1;

    @Override
    public boolean canConstruct(Inventory inventory, boolean remove) {
        List<Wood> wood = inventory.getEntities(Wood.class);

        List<Treasure> treasure = inventory.getEntities(Treasure.class);
        List<Key> keys = inventory.getEntities(Key.class);

        if (wood.size() >= NUM_WOOD && (treasure.size() >= NUM_TREASURE || keys.size() >= NUM_TREASURE)) {
            if (remove) {
                for (int i = 0; i < NUM_WOOD; i++)
                    inventory.remove(wood.get(i));
                if (treasure.size() >= NUM_TREASURE)
                    inventory.remove(treasure.get(0));
                else
                    inventory.remove(keys.get(0));
            }
            return true;
        }

        return false;
    }
}
