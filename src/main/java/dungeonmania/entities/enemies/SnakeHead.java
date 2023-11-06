package dungeonmania.entities.enemies;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import java.util.ArrayList;

import dungeonmania.Game;
import dungeonmania.battles.BattleStatistics;
import dungeonmania.entities.Entity;
import dungeonmania.entities.ItemCollector;
import dungeonmania.entities.collectables.Arrow;
import dungeonmania.entities.collectables.Collectable;
import dungeonmania.entities.collectables.Key;
import dungeonmania.entities.collectables.Treasure;
import dungeonmania.entities.collectables.potions.InvincibilityPotion;
import dungeonmania.entities.collectables.potions.InvisibilityPotion;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class SnakeHead extends Enemy implements ISnake, ItemCollector {
    private final double arrowBuff;
    private final double treasureHealthBuff;
    private final double keyHealthBuff;

    private List<SnakeBody> parts;
    private Position prevPosition;

    private boolean isInvincible;
    private boolean isInvisible;

    private final Map<Class<? extends Entity>, Runnable> foodItems = new HashMap<>() {
        {
            put(Arrow.class, () -> applyArrowBuff());
            put(Treasure.class, () -> applyTreasureBuff());
            put(Key.class, () -> applyKeyBuff());
            put(InvisibilityPotion.class, () -> applyInvisibilityBuff());
            put(InvincibilityPotion.class, () -> applyInvincibilityBuff());
        }
    };

    public SnakeHead(Position position, double health, double attack, double arrowBuff, double treasureHealthBuff,
            double keyHealthBuff, boolean isInvincible, boolean isInvisible) {
        super(position, health, attack);
        this.arrowBuff = arrowBuff;
        this.treasureHealthBuff = treasureHealthBuff;
        this.keyHealthBuff = keyHealthBuff;

        this.isInvincible = isInvincible;
        this.isInvisible = isInvisible;

        foodToEat = false;

        parts = new ArrayList<>();
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        return !(entity instanceof ISnake);
    }

    private boolean foodToEat;

    @Override
    public boolean pickUp(Collectable entity) {
        if (foodItems.keySet().contains(entity.getClass())) {
            foodToEat = true;
            foodItems.get(entity.getClass()).run();
            return true;
        }

        return false;
    }

    @Override
    public void move(Game game) {
        List<Entity> foodItems = game.getMap().getEntities().stream().filter(e -> e instanceof ISnakeFood)
                .collect(Collectors.toList());

        int shortestDistance = Integer.MAX_VALUE;
        Entity closestFood = null;

        for (Entity food : foodItems) {
            int distance = game.getMap().getDijkstraDistance(getPosition(), food.getPosition(), this);
            if (distance < shortestDistance) {
                shortestDistance = distance;
                closestFood = food;
            }
        }

        if (closestFood != null) {
            Position nextPos = game.getMap().dijkstraPathFind(getPosition(), closestFood.getPosition(), this);
            if (nextPos != null) {
                prevPosition = getPosition();
                game.getMap().moveTo(this, nextPos);
            }
        }

        Position nextPos = prevPosition;

        for (SnakeBody part : parts) {
            part.updatePosition(nextPos, game.getMap(), 0);
            nextPos = part.getPrevPosition();
        }

        if (foodToEat)
            onEat();

        foodToEat = false;
    }

    private void onEat() {
        // calculate next position
        Position newPos = prevPosition;
        if (parts.size() > 0)
            newPos = parts.get(parts.size() - 1).getPrevPosition();

        SnakeBody newPart = new SnakeBody(newPos, this);
        parts.add(newPart);
    }

    private void applyArrowBuff() {
        BattleStatistics stats = getBattleStatistics();
        stats.setAttack(stats.getAttack() + arrowBuff);
    }

    private void applyTreasureBuff() {
        BattleStatistics stats = getBattleStatistics();
        stats.setHealth(stats.getHealth() + treasureHealthBuff);
    }

    private void applyKeyBuff() {
        BattleStatistics stats = getBattleStatistics();
        stats.setHealth(stats.getHealth() * keyHealthBuff);
    }

    private void applyInvisibilityBuff() {
        isInvisible = true;
    }

    private void applyInvincibilityBuff() {
        isInvincible = true;
    }

    public boolean isInvisible() {
        return isInvisible;
    }

    @Override
    public void onDestroy(GameMap map) {
        map.destroyEntity(parts.get(0));
        super.onDestroy(map);
    }

    protected void removeBody(SnakeBody part, GameMap map) {
        boolean remove = false;

        SnakeBody nextPart = null;
        for (SnakeBody p : parts) {
            if (remove) {
                nextPart = p;
                break;
            }

            if (p == part)
                remove = true;
        }

        if (nextPart != null)
            map.destroyEntity(nextPart);
    }
}
