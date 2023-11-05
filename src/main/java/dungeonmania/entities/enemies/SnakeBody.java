package dungeonmania.entities.enemies;

import dungeonmania.entities.Entity;
import dungeonmania.util.Position;

public class SnakeBody extends Entity {
    private Position position;
    private Position prevPosition;

    private SnakeBody nextSnakeBody = null;
    private SnakeBody prevSnakeBody = null;

    public SnakeBody(Position position) {
        super(position);
        this.position = position;
    }

    public void updatePosition(Position position) {
        prevPosition = this.position;
        this.position = position;
        super.setPosition(position);
    }

    public Position getPrevPosition() {
        return prevPosition;
    }
}
