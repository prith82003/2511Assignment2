package dungeonmania.entities.enemies;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

public class SnakeHead extends MovingEnemy implements ISnake, ItemCollector {
    private final double arrowBuff;
    private final double treasureHealthBuff;
    private final double keyHealthBuff;

    private List<SnakeBody> parts;

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
            double keyHealthBuff) {
        super(position, health, attack);
        this.arrowBuff = arrowBuff;
        this.treasureHealthBuff = treasureHealthBuff;
        this.keyHealthBuff = keyHealthBuff;

        this.isInvincible = false;
        this.isInvisible = false;

        foodToEat = false;

        parts = new LinkedList<>();
    }

    protected SnakeHead(Position position, SnakeHead snake, List<SnakeBody> parts) {
        super(position, snake.getHealth(), snake.getAttack());
        this.arrowBuff = snake.arrowBuff;
        this.treasureHealthBuff = snake.treasureHealthBuff;
        this.keyHealthBuff = snake.keyHealthBuff;

        this.isInvincible = snake.isInvincible;
        this.isInvisible = snake.isInvisible;

        foodToEat = false;

        this.parts = new LinkedList<>();
        this.parts.addAll(parts);
        parts.forEach((e) -> e.setHead(this));
        GameMap.getInstance().moveTo(this, position);
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
                game.getMap().moveTo(this, nextPos);
            }

            nextPos = getPreviousPosition();

            for (int i = 0; i < parts.size(); i++) {
                SnakeBody part = parts.get(i);
                part.updatePosition(nextPos, game.getMap());
                nextPos = part.getPreviousPosition();
            }
        }

        if (foodToEat)
            onEat(game.getMap());

        foodToEat = false;
    }

    private void onEat(GameMap map) {
        Position newPos = getPreviousPosition();
        if (parts.size() > 0)
            newPos = parts.get(parts.size() - 1).getPreviousPosition();

        SnakeBody newPart = new SnakeBody(newPos, this, map);
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

    protected boolean isInvincible() {
        return isInvincible;
    }

    @Override
    public void onDestroy(GameMap map) {
        isInvincible = false;
        isInvisible = false;
        for (int i = parts.size() - 1; i >= 0; i--) {
            SnakeBody part = parts.get(i);
            parts.remove(i);
            map.destroyEntity(part);
        }
        super.onDestroy(map);
    }

    protected void removeBody(SnakeBody part, GameMap map) {
        int indexOfTarget = parts.indexOf(part);
        if (indexOfTarget != -1) {
            List<SnakeBody> partsToRemove = new ArrayList<>();

            for (int i = indexOfTarget + 1; i < parts.size(); i++) {
                partsToRemove.add(parts.get(i));
            }

            partsToRemove.forEach(map::destroyEntity);
            parts.subList(indexOfTarget + 1, parts.size()).clear();
        }
    }

    protected void detachBody(SnakeBody part, GameMap map) {
        int indexOfTarget = parts.indexOf(part);
        if (indexOfTarget != -1 && indexOfTarget < parts.size() - 1) {
            SnakeBody partToDestroy = parts.remove(indexOfTarget + 1);
            map.destroyEntity(partToDestroy);

            var spawnPos = partToDestroy.getPosition();
            List<SnakeBody> newBodyParts = new ArrayList<>();
            if (indexOfTarget + 1 < parts.size()) {
                newBodyParts = new ArrayList<>(parts.subList(indexOfTarget + 1, parts.size()));

                parts.subList(indexOfTarget + 1, parts.size()).clear();
            }

            SnakeHead newHead = new SnakeHead(spawnPos, this, newBodyParts);

            map.addEntity(newHead);

            map.getGame().register(() -> newHead.move(map.getGame()), Game.AI_MOVEMENT, newHead.getId());
        }
    }

    public double getHealth() {
        return getBattleStatistics().getHealth();
    }

    public double getAttack() {
        return getBattleStatistics().getAttack();
    }
}
