package dungeonmania.entities.enemies;

import dungeonmania.battles.BattleStatistics;
import dungeonmania.entities.Entity;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class SnakeBody extends Enemy implements ISnake {
    private Position position;
    private Position prevPosition;

    private SnakeHead head;

    public SnakeBody(Position position, SnakeHead head, GameMap map) {
        super(position, head.getHealth(), head.getAttack());
        this.position = position;
        this.prevPosition = position;
        this.head = head;

        map.moveTo(this, position);

    }

    protected void setHead(SnakeHead head) {
        this.head = head;
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        return !(entity instanceof ISnake);
    }

    @Override
    public BattleStatistics getBattleStatistics() {
        return head.getBattleStatistics();
    }

    public void updatePosition(Position position, GameMap map) {
        if (!prevPosition.equals(this.position))
            prevPosition = this.position;
        this.position = position;
        map.moveTo(this, position);
    }

    public boolean isInvisible() {
        return head.isInvisible();
    }

    @Override
    public void onDestroy(GameMap map) {
        if (head.isInvincible())
            head.detachBody(this, map);
        else
            head.removeBody(this, map);

        super.onDestroy(map);
    }
}
