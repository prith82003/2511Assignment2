package dungeonmania.entities.buildables;

import dungeonmania.Game;
import dungeonmania.battles.BattleStatistics;
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
        return BattleStatistics.applyBuff(origin, new BattleStatistics(0, attack, defense, 1, 1));
    }

    @Override
    public int getDurability() {
        return Integer.MAX_VALUE;
    }
}
