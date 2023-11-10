package dungeonmania.entities.collectables;

import dungeonmania.entities.enemies.ISnakeFood;
import dungeonmania.util.Position;

public class Treasure extends Collectable implements ISnakeFood {
    public Treasure(Position position) {
        super(position);
    }
}
