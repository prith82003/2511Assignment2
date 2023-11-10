package dungeonmania.entities.enemies;

import dungeonmania.Game;
import dungeonmania.battles.BattleStatistics;
import dungeonmania.battles.BattleStatisticsBuilder;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Interactable;
import dungeonmania.entities.Player;
import dungeonmania.entities.buildables.Sceptre;
import dungeonmania.entities.collectables.SunStone;
import dungeonmania.entities.collectables.Treasure;
import dungeonmania.entities.collectables.potions.InvincibilityPotion;
import dungeonmania.entities.collectables.potions.InvisibilityPotion;
import dungeonmania.entities.inventory.Inventory;
import dungeonmania.entities.inventory.InventoryItem;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

import java.util.ArrayList;
import java.util.List;
// import java.util.stream.Collectors;

public class Mercenary extends MovingEnemy implements Interactable {
    public static final int DEFAULT_BRIBE_AMOUNT = 1;
    public static final int DEFAULT_BRIBE_RADIUS = 1;
    public static final double DEFAULT_ATTACK = 5.0;
    public static final double DEFAULT_HEALTH = 10.0;

    private int bribeAmount = Mercenary.DEFAULT_BRIBE_AMOUNT;
    private int bribeRadius = Mercenary.DEFAULT_BRIBE_RADIUS;
    private int mindControlDuration;

    private double allyAttack;
    private double allyDefence;
    private boolean allied = false;
    private boolean isAdjacentToPlayer = false;
    private boolean mindControlled = false;

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
        if (allied || mindControlled) {
            return false;
        }
        return inRange(player)
                && (player.countEntityOfType(Treasure.class) - player.countEntityOfType(SunStone.class)) >= bribeAmount;
    }

    public boolean inRange(Player player) {
        double xDist = this.getPosition().getX() - player.getPosition().getX();
        double yDist = this.getPosition().getY() - player.getPosition().getY();
        return (Math.sqrt(Math.pow(xDist, 2) + Math.pow(yDist, 2)) <= bribeRadius);
    }

    private boolean canBeMindControlled(Player player) {
        if (allied || mindControlled) {
            return false;
        }

        Inventory inventory = player.getInventory();
        Sceptre sceptre = inventory.getFirst(Sceptre.class);

        return (sceptre != null && sceptre.getDurability() >= 0) ? true : false;
    }

    /**
     * bribe the merc
     */
    private void bribe(Player player) {
        allied = true;
        List<Treasure> treasuresToBribeWith = new ArrayList<>();
        List<Treasure> inventoryItems = player.getInventory().getEntities(Treasure.class);

        int count = 0;
        for (Treasure item : inventoryItems) {
            if (!(item instanceof SunStone) && count < bribeAmount) {
                treasuresToBribeWith.add(item);
                count++;
            }
            if (count >= bribeAmount) {
                break;
            }
        }

        for (int i = 0; i < treasuresToBribeWith.size(); i++) {
            player.use(Treasure.class);
        }
    }

    private void mindControl(Player player) {
        mindControlled = true;
        Inventory inventory = player.getInventory();
        Sceptre sceptre = inventory.getFirst(Sceptre.class);
        sceptre.setDurability(sceptre.getDurability() - 1);
        if (sceptre.getDurability() <= 0) {
            player.use(Sceptre.class);
        }
    }

    public boolean isSunstone(InventoryItem item) {
        return (SunStone.class.isInstance(item));
    }

    @Override
    public void interact(Player player, Game game) {
        if (canBeBribed(player)) {
            bribe(player);
        } else if (canBeMindControlled(player)) {
            Inventory inventory = player.getInventory();
            Sceptre sceptre = inventory.getFirst(Sceptre.class);
            mindControlDuration = sceptre.getMindControlDuration();
            mindControl(player);
        }
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
        checkmindControlled();
        if (allied) {
            nextPos = moveToward(map, player);
        } else if (mindControlled) {
            nextPos = moveToward(map, player);
            mindControlDuration--;
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

    public void checkmindControlled() {
        if (mindControlDuration <= 0) {
            mindControlled = false;
        }
    }

    @Override
    public boolean isInteractable(Player player) {
        return canBeBribed(player) || canBeMindControlled(player);
    }

    private static final double ALLIED_MAGNIFIER = 1;
    private static final double ALLIED_REDUCER = 1;

    @Override
    public BattleStatistics getBattleStatistics() {
        if (!allied)
            return super.getBattleStatistics();

        BattleStatisticsBuilder builder = new BattleStatisticsBuilder();
        builder.setAttack(allyAttack).setDefence(allyDefence).setMagnifier(ALLIED_MAGNIFIER).setReducer(ALLIED_REDUCER);
        return builder.build();
    }
}
