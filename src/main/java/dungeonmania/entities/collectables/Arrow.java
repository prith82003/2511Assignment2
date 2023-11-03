package dungeonmania.entities.collectables;

import dungeonmania.entities.Entity;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class Arrow extends Collectable {
    public Arrow(Position position) {
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
