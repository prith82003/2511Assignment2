package dungeonmania.entities.collectables;

import dungeonmania.entities.Entity;
import dungeonmania.entities.IOverlappable;
import dungeonmania.entities.ItemCollector;
import dungeonmania.entities.inventory.InventoryItem;
import dungeonmania.util.Position;
import dungeonmania.map.GameMap;

public abstract class Collectable extends Entity implements InventoryItem, IOverlappable {
    public Collectable(Position position) {
        super(position);
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        return true;
    }

    @Override
    public void onOverlap(GameMap map, Entity entity) {
        if (entity instanceof ItemCollector) {
            if (!((ItemCollector) entity).pickUp(this))
                return;
            map.destroyEntity(this);
        }
    }
}
