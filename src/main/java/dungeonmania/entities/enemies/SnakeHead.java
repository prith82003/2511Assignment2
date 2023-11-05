package dungeonmania.entities.enemies;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import dungeonmania.Game;
import dungeonmania.battles.BattleStatistics;
import dungeonmania.battles.BattleStatisticsBuilder;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Player;
import dungeonmania.entities.collectables.Arrow;
import dungeonmania.entities.collectables.Key;
import dungeonmania.entities.collectables.Treasure;
import dungeonmania.entities.collectables.potions.Potion;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class SnakeHead extends Enemy {
    private final double arrowBuff;
    private final double treasureHealthBuff;
    private final double keyHealthBuff;

    private List<SnakeBody> parts;
    private Position prevPosition;

    private static Map<Class<? extends Entity>, BattleStatistics> FOOD_ITEMS = new HashMap<>() {
        {
            put(Arrow.class, );
        }
    };

    public SnakeHead(Position position, double health, double attack, double arrowBuff, double treasureHealthBuff,
            double keyHealthBuff) {
        super(position, health, attack);
        this.arrowBuff = arrowBuff;
        this.treasureHealthBuff = treasureHealthBuff;
        this.keyHealthBuff = keyHealthBuff;

        parts = new ArrayList<>();
        initFoodMap();
    }

    private void initFoodMap() {

    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        return true;
    }

    @Override
    public void onOverlap(GameMap map, Entity entity) {
        if (FOOD_ITEMS.contains(entity.getClass())) {
            onEat();
            map.destroyEntity(entity);
        }
    }

    @Override
    public void move(Game game) {

    }

    private void onEat(SnakeBody part) {
        // calculate next position
        Position newPos = prevPosition;
        if (parts.size() > 0)
            newPos = parts.get(parts.size() - 1).getPrevPosition();

        SnakeBody newPart = new SnakeBody(newPos);
        // Add new body part
    }

    private void applyFoodBuff(Class<? extends Entity> foodClass) {

    }
}
