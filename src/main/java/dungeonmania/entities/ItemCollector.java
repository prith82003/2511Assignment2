package dungeonmania.entities;

import dungeonmania.entities.collectables.Collectable;

public interface ItemCollector {
    public boolean pickUp(Collectable item);
}
