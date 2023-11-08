package dungeonmania.entities.enemies;

import dungeonmania.Game;
import dungeonmania.battles.BattleStatistics;
import dungeonmania.entities.Entity;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class SnakeBody extends Enemy implements ISnake {
    private Position position;
    private Position prevPosition;

    private SnakeHead head;

    public SnakeBody(Position position, SnakeHead head, GameMap map, int i) {
        super(position, head.getHealth(), head.getAttack());
        init(position, head, map);
    }

    protected void init(Position position, SnakeHead head, GameMap map) {
        this.position = position;
        this.prevPosition = position;

        map.moveTo(this, position);

        this.head = head;
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
        if (head.isInvincible()) {
            System.out.println("Invincible");
            head.detachBody(this, map);
        } else {
            System.out.println("Not invincible");
            head.removeBody(this, map);
        }

        super.onDestroy(map);
    }
}
