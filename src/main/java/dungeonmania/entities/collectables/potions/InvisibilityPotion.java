package dungeonmania.entities.collectables.potions;

import dungeonmania.battles.BattleStatistics;
import dungeonmania.battles.BattleStatisticsBuilder;
import dungeonmania.util.Position;

public class InvisibilityPotion extends Potion {
    public static final int DEFAULT_DURATION = 8;

    private static final double POTION_HEALTH = 0;
    private static final double POTION_ATTACK = 0;
    private static final double POTION_DEFENCE = 0;
    private static final double POTION_MAGNIFIER = 1;
    private static final double POTION_REDUCER = 1;
    private static final boolean POTION_INVINCIBLE = false;
    private static final boolean POTION_ENABLED = false;

    public InvisibilityPotion(Position position, int duration) {
        super(position, duration);
    }

    @Override
    public BattleStatistics applyBuff(BattleStatistics origin) {
        BattleStatisticsBuilder builder = new BattleStatisticsBuilder();
        builder.setHealth(POTION_HEALTH).setAttack(POTION_ATTACK).setDefence(POTION_DEFENCE)
                .setMagnifier(POTION_MAGNIFIER).setReducer(POTION_REDUCER).setInvincible(POTION_INVINCIBLE)
                .setEnabled(POTION_ENABLED);
        return BattleStatistics.applyBuff(origin, builder.build());
    }

}
