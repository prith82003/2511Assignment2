package dungeonmania.entities.collectables;

import dungeonmania.entities.enemies.ISnakeFood;
import dungeonmania.util.Position;

public class Arrow extends Collectable implements ISnakeFood {
    public Arrow(Position position) {
        super(position);
    }
}
