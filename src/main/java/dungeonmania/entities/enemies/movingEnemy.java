package dungeonmania.entities.enemies;

import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import dungeonmania.Game;
import dungeonmania.map.GameMap;

public abstract class movingEnemy extends Enemy {
    public movingEnemy(Position position, double health, double attack) {
        super(position, health, attack);
    }

    public Position moveAway(GameMap map) {
        Position plrDiff = Position.calculatePositionBetween(map.getPlayer().getPosition(), getPosition());

        Position moveX = (plrDiff.getX() >= 0) ? Position.translateBy(getPosition(), Direction.RIGHT)
                : Position.translateBy(getPosition(), Direction.LEFT);
        Position moveY = (plrDiff.getY() >= 0) ? Position.translateBy(getPosition(), Direction.UP)
                : Position.translateBy(getPosition(), Direction.DOWN);
        Position offset = getPosition();
        if (plrDiff.getY() == 0 && map.canMoveTo(this, moveX))
            offset = moveX;
        else if (plrDiff.getX() == 0 && map.canMoveTo(this, moveY))
            offset = moveY;
        else if (Math.abs(plrDiff.getX()) >= Math.abs(plrDiff.getY())) {
            if (map.canMoveTo(this, moveX))
                offset = moveX;
            else if (map.canMoveTo(this, moveY))
                offset = moveY;
            else
                offset = getPosition();
        } else {
            if (map.canMoveTo(this, moveY))
                offset = moveY;
            else if (map.canMoveTo(this, moveX))
                offset = moveX;
            else
                offset = getPosition();
        }
        return offset;
    }

    public Position moveRandom(GameMap map) {
        Random randGen = new Random();
        List<Position> pos = getPosition().getCardinallyAdjacentPositions();
        pos = pos.stream().filter(p -> map.canMoveTo(this, p)).collect(Collectors.toList());
        if (pos.size() == 0) {
            return getPosition();
        } else {
            return pos.get(randGen.nextInt(pos.size()));
        }
    }

    public abstract void move(Game game);
}
