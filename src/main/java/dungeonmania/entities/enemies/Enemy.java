package dungeonmania.entities.enemies;

import dungeonmania.Game;
import dungeonmania.battles.BattleStatistics;
import dungeonmania.battles.BattleStatisticsBuilder;
import dungeonmania.battles.Battleable;
import dungeonmania.entities.Entity;
import dungeonmania.entities.IDestroyable;
import dungeonmania.entities.IOverlappable;
import dungeonmania.entities.Player;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public abstract class Enemy extends Entity implements Battleable, IOverlappable, IDestroyable {
    private BattleStatistics battleStatistics;

    private static final double ENEMY_DEFENCE = 0;

    public Enemy(Position position, double health, double attack) {
        super(position.asLayer(Entity.CHARACTER_LAYER));
        BattleStatisticsBuilder builder = new BattleStatisticsBuilder();
        builder.setHealth(health).setAttack(attack).setDefence(ENEMY_DEFENCE)
                .setMagnifier(BattleStatistics.DEFAULT_DAMAGE_MAGNIFIER)
                .setReducer(BattleStatistics.DEFAULT_ENEMY_DAMAGE_REDUCER);

        battleStatistics = builder.build();
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        return entity instanceof Player;
    }

    @Override
    public BattleStatistics getBattleStatistics() {
        return battleStatistics;
    }

    @Override
    public void onOverlap(GameMap map, Entity entity) {
        if (entity instanceof Player) {
            Player player = (Player) entity;
            map.getGame().battle(player, this);
        }
    }

    @Override
    public void onDestroy(GameMap map) {
        Game g = map.getGame();
        g.unsubscribe(getId());
    }
}
