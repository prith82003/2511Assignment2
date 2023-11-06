package dungeonmania.entities.buildables;

import dungeonmania.Game;
import dungeonmania.battles.BattleStatistics;
import dungeonmania.battles.BattleStatisticsBuilder;

public class Bow extends Buildable {
    private int durability;
    private static final double BOW_MAGNIFIER = 2;
    private static final double BOW_REDUCER = 1;

    public Bow(int durability) {
        super(null);
        this.durability = durability;
    }

    @Override
    public void use(Game game) {
        durability--;
        if (durability <= 0) {
            game.getPlayer().remove(this);
        }
    }

    @Override
    public BattleStatistics applyBuff(BattleStatistics origin) {
        BattleStatisticsBuilder builder = new BattleStatisticsBuilder();
        builder.setMagnifier(BOW_MAGNIFIER).setReducer(BOW_REDUCER);
        return BattleStatistics.applyBuff(origin, builder.build());
    }

    @Override
    public int getDurability() {
        return durability;
    }
}
