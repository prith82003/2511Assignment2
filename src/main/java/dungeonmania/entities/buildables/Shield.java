package dungeonmania.entities.buildables;

import dungeonmania.Game;
import dungeonmania.battles.BattleStatistics;
import dungeonmania.battles.BattleStatisticsBuilder;

public class Shield extends Buildable {
    private int durability;
    private double defence;

    private static final double SHIELD_MAGNIFIER = 1;
    private static final double SHIELD_REDUCER = 1;

    public Shield(int durability, double defence) {
        super(null);
        this.durability = durability;
        this.defence = defence;
    }

    @Override
    public void use(Game game) {
        durability--;
        if (durability <= 0) {
            game.getPlayer().remove(this);
        }
    }

    // HAVE SCEPTRE AND MIDNIGHT ARMOUT HERE FOR BUILDIING
    // FOR EACH CREATE SCRPETRE BUILDABLE AND ARMOUTR BUILDABLE

    @Override
    public BattleStatistics applyBuff(BattleStatistics origin) {
        BattleStatisticsBuilder builder = new BattleStatisticsBuilder();
        builder.setDefence(defence).setMagnifier(SHIELD_MAGNIFIER).setReducer(SHIELD_REDUCER);
        return BattleStatistics.applyBuff(origin, builder.build());
    }

    @Override
    public int getDurability() {
        return durability;
    }
}
