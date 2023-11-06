package dungeonmania.entities.enemies;

import dungeonmania.Game;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class SnakeBody extends Enemy implements ISnake {
    private Position position;
    private Position prevPosition;

    private SnakeHead head;

    public SnakeBody(Position position, SnakeHead head) {
        super(position, head.getBattleStatistics().getHealth(), head.getBattleStatistics().getAttack());
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

    @Override
    public void move(Game game) {
    }

    @Override
    public void onDestroy(GameMap map) {
        head.removeBody(this, map);
        super.onDestroy(map);
    }
}
