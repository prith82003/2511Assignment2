package dungeonmania.entities.collectables.potions;

import dungeonmania.battles.BattleStatistics;
import dungeonmania.battles.BattleStatisticsBuilder;
import dungeonmania.util.Position;

public class InvisibilityPotion extends Potion {
    public static final int DEFAULT_DURATION = 8;

    public InvisibilityPotion(Position position, int duration) {
        super(position, duration);
    }

    @Override
    public BattleStatistics applyBuff(BattleStatistics origin) {
        BattleStatisticsBuilder builder = new BattleStatisticsBuilder();
        return BattleStatistics.applyBuff(origin, new BattleStatistics(0, 0, 0, 1, 1, false, false));
    }

}
