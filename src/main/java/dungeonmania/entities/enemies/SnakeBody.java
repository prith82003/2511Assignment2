package dungeonmania.entities.enemies;

import dungeonmania.entities.Entity;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class SnakeBody extends Entity implements ISnake {
    private Position position;
    private Position prevPosition;

    private SnakeHead head;

    public SnakeBody(Position position, SnakeHead head) {
        super(position);
        this.position = position;
        this.prevPosition = position;

        this.head = head;
    }

    public void updatePosition(Position position, GameMap map, int i) {
        prevPosition = this.position;
        this.position = position;
        map.moveTo(this, position);
    }

    public Position getPrevPosition() {
        return prevPosition;
    }

    public boolean isInvisible() {
        return head.isInvisible();
    }
}
