package dungeonmania.entities.enemies;

import dungeonmania.Game;
import dungeonmania.battles.BattleStatistics;
import dungeonmania.battles.BattleStatisticsBuilder;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Interactable;
import dungeonmania.entities.Player;
import dungeonmania.entities.collectables.Treasure;
import dungeonmania.entities.collectables.potions.InvincibilityPotion;
import dungeonmania.entities.collectables.potions.InvisibilityPotion;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class Mercenary extends MovingEnemy implements Interactable {
    public static final int DEFAULT_BRIBE_AMOUNT = 1;
    public static final int DEFAULT_BRIBE_RADIUS = 1;
    public static final double DEFAULT_ATTACK = 5.0;
    public static final double DEFAULT_HEALTH = 10.0;

    private int bribeAmount = Mercenary.DEFAULT_BRIBE_AMOUNT;
    private int bribeRadius = Mercenary.DEFAULT_BRIBE_RADIUS;

    private double allyAttack;
    private double allyDefence;
    private boolean allied = false;
    private boolean isAdjacentToPlayer = false;

    public Mercenary(Position position, double health, double attack, int bribeAmount, int bribeRadius,
            double allyAttack, double allyDefence) {
        super(position, health, attack);
        this.bribeAmount = bribeAmount;
        this.bribeRadius = bribeRadius;
        this.allyAttack = allyAttack;
        this.allyDefence = allyDefence;
    }

    public boolean isAllied() {
        return allied;
    }

    @Override
    public void onOverlap(GameMap map, Entity entity) {
        if (allied)
            return;
        super.onOverlap(map, entity);
    }

    /**
     * check whether the current merc can be bribed
     * @param player
     * @return
     */
    private boolean canBeBribed(Player player) {
        return bribeRadius >= 0 && player.countEntityOfType(Treasure.class) >= bribeAmount;
    }

    /**
     * bribe the merc
     */
    private void bribe(Player player) {
        for (int i = 0; i < bribeAmount; i++) {
            player.use(Treasure.class);
        }

    }

    @Override
    public void interact(Player player, Game game) {
        allied = true;
        bribe(player);
        if (!isAdjacentToPlayer && Position.isAdjacent(player.getPosition(), getPosition()))
            isAdjacentToPlayer = true;
    }

    public Position moveToward(GameMap map, Player player) {
        Position nextPos;
        nextPos = isAdjacentToPlayer ? player.getPreviousDistinctPosition()
                : map.dijkstraPathFind(getPosition(), player.getPosition(), this);
        if (!isAdjacentToPlayer && Position.isAdjacent(player.getPosition(), nextPos))
            isAdjacentToPlayer = true;
        return nextPos;
    }

    @Override
    public void move(Game game) {
        Position nextPos;
        GameMap map = game.getMap();
        Player player = game.getPlayer();
        if (allied) {
            nextPos = moveToward(map, player);
        } else if (map.getPlayer().getEffectivePotion() instanceof InvisibilityPotion) {
            nextPos = moveRandom(map);
        } else if (map.getPlayer().getEffectivePotion() instanceof InvincibilityPotion) {
            nextPos = moveAway(map);
        } else {
            // Follow hostile
            nextPos = map.dijkstraPathFind(getPosition(), player.getPosition(), this);
        }
        map.moveTo(this, nextPos);
    }

    @Override
    public boolean isInteractable(Player player) {
        return !allied && canBeBribed(player);
    }

    private static final double ALLIED_HEALTH = 0;
    private static final double ALLIED_MAGNIFIER = 1;
    private static final double ALLIED_REDUCER = 1;

    @Override
    public BattleStatistics getBattleStatistics() {
        if (!allied)
            return super.getBattleStatistics();

        BattleStatisticsBuilder builder = new BattleStatisticsBuilder();
        builder.setHealth(ALLIED_HEALTH).setAttack(allyAttack).setDefence(allyDefence).setMagnifier(ALLIED_MAGNIFIER)
                .setReducer(ALLIED_REDUCER);
        return builder.build();
    }
}
