package dungeonmania.entities.collectables;

import dungeonmania.entities.enemies.ISnakeFood;
import dungeonmania.util.Position;

public class Key extends Collectable implements ISnakeFood {
    private int number;

    public Key(Position position, int number) {
        super(position);
        this.number = number;
    }

    public int getnumber() {
        return number;
    }

}
