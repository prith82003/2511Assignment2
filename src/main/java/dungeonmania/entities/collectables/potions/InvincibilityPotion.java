package dungeonmania.entities.collectables.potions;

import dungeonmania.battles.BattleStatistics;
import dungeonmania.battles.BattleStatisticsBuilder;
import dungeonmania.util.Position;

public class InvincibilityPotion extends Potion {
    public static final int DEFAULT_DURATION = 8;

    private static final double POTION_MAGNIFIER = 1;
    private static final double POTION_REDUCER = 1;
    private static final boolean POTION_INVINCIBLE = true;
    private static final boolean POTION_ENABLED = true;

    public InvincibilityPotion(Position position, int duration) {
        super(position, duration);
    }

    @Override
    public BattleStatistics applyBuff(BattleStatistics origin) {
        BattleStatisticsBuilder builder = new BattleStatisticsBuilder();
        builder.setMagnifier(POTION_MAGNIFIER).setReducer(POTION_REDUCER).setInvincible(POTION_INVINCIBLE)
                .setEnabled(POTION_ENABLED);

        return BattleStatistics.applyBuff(origin, builder.build());
    }
}
