package dungeonmania.entities.inventory;

import java.util.List;

import dungeonmania.entities.collectables.Arrow;
import dungeonmania.entities.collectables.Wood;

public class BowBuildable implements IBuildable {
    private static final int NUM_WOOD = 1;
    private static final int NUM_ARROWS = 3;

    @Override
    public boolean canConstruct(Inventory inventory, boolean remove) {
        List<Wood> wood = inventory.getEntities(Wood.class);
        List<Arrow> arrows = inventory.getEntities(Arrow.class);

        if (wood.size() >= NUM_WOOD && arrows.size() >= NUM_ARROWS) {
            if (remove) {
                for (int i = 0; i < NUM_WOOD; i++)
                    inventory.remove(wood.get(i));
                for (int i = 0; i < NUM_ARROWS; i++)
                    inventory.remove(arrows.get(i));
            }

            return true;
        }

        return false;
    }
}
