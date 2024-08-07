package dungeonmania.entities;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import dungeonmania.battles.BattleStatistics;
import dungeonmania.battles.BattleStatisticsBuilder;
import dungeonmania.battles.Battleable;
import dungeonmania.entities.collectables.Bomb;
import dungeonmania.entities.collectables.Collectable;
import dungeonmania.entities.collectables.Treasure;
import dungeonmania.entities.collectables.potions.InvincibilityPotion;
import dungeonmania.entities.collectables.potions.Potion;
import dungeonmania.entities.enemies.Enemy;
import dungeonmania.entities.enemies.Mercenary;
import dungeonmania.entities.entity_factory.EntityFactory;
import dungeonmania.entities.inventory.Inventory;
import dungeonmania.entities.inventory.InventoryItem;
import dungeonmania.entities.playerState.BaseState;
import dungeonmania.entities.playerState.PlayerState;
import dungeonmania.map.GameMap;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Player extends Entity implements Battleable, IOverlappable, ItemCollector {
    public static final double DEFAULT_ATTACK = 5.0;
    public static final double DEFAULT_HEALTH = 5.0;
    private BattleStatistics battleStatistics;
    private Inventory inventory;
    private Queue<Potion> queue = new LinkedList<>();
    private Potion inEffective = null;
    private int nextTrigger = 0;

    private int collectedTreasureCount = 0;

    private PlayerState state;

    private int numEnemiesKilled = 0;

    public Player(Position position, double health, double attack) {
        super(position);
        BattleStatisticsBuilder builder = new BattleStatisticsBuilder();
        builder.setHealth(health).setAttack(attack).setMagnifier(BattleStatistics.DEFAULT_DAMAGE_MAGNIFIER)
                .setReducer(BattleStatistics.DEFAULT_PLAYER_DAMAGE_REDUCER);

        battleStatistics = builder.build();
        inventory = new Inventory();
        state = new BaseState(this);
    }

    public int getCollectedTreasureCount() {
        return collectedTreasureCount;
    }

    public boolean hasWeapon() {
        return inventory.hasWeapon();
    }

    public BattleItem getWeapon() {
        return inventory.getWeapon();
    }

    public List<String> getBuildables() {
        return inventory.getBuildables();
    }

    public boolean build(String entity, EntityFactory factory) {
        InventoryItem item = inventory.checkBuildCriteria(this, true, entity, factory);
        if (item == null)
            return false;
        return inventory.add(item);
    }

    public void move(GameMap map, Direction direction) {
        this.setFacing(direction);
        map.moveTo(this, Position.translateBy(this.getPosition(), direction));
    }

    @Override
    public void onOverlap(GameMap map, Entity entity) {
        if (entity instanceof Enemy) {
            if (entity instanceof Mercenary) {
                if (((Mercenary) entity).isAllied())
                    return;
            }
            map.getGame().battle(this, (Enemy) entity);
        }
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        return true;
    }

    public Entity getEntity(String itemUsedId) {
        return inventory.getEntity(itemUsedId);
    }

    @Override
    public boolean pickUp(Collectable item) {
        if (item instanceof Treasure)
            collectedTreasureCount++;
        return inventory.add((InventoryItem) item);
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Potion getEffectivePotion() {
        return inEffective;
    }

    public <T extends InventoryItem> void use(Class<T> itemType) {
        T item = inventory.getFirst(itemType);
        if (item != null)
            inventory.remove(item);
    }

    public void use(Bomb bomb, GameMap map) {
        inventory.remove(bomb);
        bomb.onPutDown(map, getPosition());
    }

    public void triggerNext(int currentTick) {
        if (queue.isEmpty()) {
            inEffective = null;
            state.transitionBase();
            return;
        }
        inEffective = queue.remove();
        if (inEffective instanceof InvincibilityPotion) {
            state.transitionInvincible();
        } else {
            state.transitionInvisible();
        }
        nextTrigger = currentTick + inEffective.getDuration();
    }

    public void changeState(PlayerState playerState) {
        state = playerState;
    }

    public void use(Potion potion, int tick) {
        inventory.remove(potion);
        queue.add(potion);
        if (inEffective == null) {
            triggerNext(tick);
        }
    }

    public void onTick(int tick) {
        if (inEffective == null || tick == nextTrigger) {
            triggerNext(tick);
        }
    }

    public void remove(InventoryItem item) {
        inventory.remove(item);
    }

    @Override
    public BattleStatistics getBattleStatistics() {
        return battleStatistics;
    }

    public <T extends InventoryItem> int countEntityOfType(Class<T> itemType) {
        return inventory.count(itemType);
    }

    private static final double INVINCIBLE_MAGNIFIER = 1;
    private static final double INVINCIBLE_REDUCER = 1;
    private static final boolean INVINCIBLE_INVINCIBLE = true;
    private static final boolean INVINCIBLE_ENABLED = true;

    private static final boolean INVISIBILE_ENABLED = false;

    public BattleStatistics applyBuff(BattleStatistics origin) {
        if (state.isInvincible()) {
            BattleStatisticsBuilder builder = new BattleStatisticsBuilder();
            builder.setMagnifier(INVINCIBLE_MAGNIFIER).setReducer(INVINCIBLE_REDUCER)
                    .setInvincible(INVINCIBLE_INVINCIBLE).setEnabled(INVINCIBLE_ENABLED);

            return BattleStatistics.applyBuff(origin, builder.build());
        } else if (state.isInvisible()) {
            BattleStatisticsBuilder builder = new BattleStatisticsBuilder();
            builder.setMagnifier(INVINCIBLE_MAGNIFIER).setReducer(INVINCIBLE_REDUCER).setEnabled(INVISIBILE_ENABLED);

            return BattleStatistics.applyBuff(origin, builder.build());
        }
        return origin;
    }

    public void incrementKilled() {
        numEnemiesKilled++;
    }

    public int getNumEnemiesKilled() {
        return numEnemiesKilled;
    }
}
