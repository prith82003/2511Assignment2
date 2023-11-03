package dungeonmania.entities.collectables;

import dungeonmania.entities.Entity;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class Treasure extends Collectable {
    public Treasure(Position position) {
        super(position);
    }

    @Override
    public void onMovedAway(GameMap map, Entity entity) {
        return;
    }

    @Override
    public void onDestroy(GameMap gameMap) {
        return;
    }
}
