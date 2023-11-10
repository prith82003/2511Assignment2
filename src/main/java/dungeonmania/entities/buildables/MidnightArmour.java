package dungeonmania.entities.buildables;

import dungeonmania.Game;
import dungeonmania.battles.BattleStatistics;
import dungeonmania.battles.BattleStatisticsBuilder;
import dungeonmania.entities.BattleItem;

public class MidnightArmour extends Buildable implements BattleItem {
    private int attack;
    private int defense;

    public MidnightArmour(int attack, int defense) {
        super(null);
        this.attack = attack;
        this.defense = defense;
    }

    @Override
    public void use(Game game) {
    }

    @Override
    public BattleStatistics applyBuff(BattleStatistics origin) {
        BattleStatisticsBuilder builder = new BattleStatisticsBuilder();
        builder.setAttack(attack).setDefence(defense).setMagnifier(1).setReducer(1);
        return BattleStatistics.applyBuff(origin, builder.build());
    }

    @Override
    public int getDurability() {
        return Integer.MAX_VALUE;
    }
}
