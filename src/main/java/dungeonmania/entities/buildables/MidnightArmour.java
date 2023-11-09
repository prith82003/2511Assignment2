package dungeonmania.entities.buildables;

import dungeonmania.Game;
import dungeonmania.battles.BattleStatistics;
import dungeonmania.entities.BattleItem;

public class MidnightArmour extends Buildable implements BattleItem {
    private int midnightArmourAttack;
    private int midnightArmourDefense;

    public MidnightArmour(int midnightArmourAttack, int midnightArmourDefense) {
        super(null);
        this.midnightArmourAttack = midnightArmourAttack;
        this.midnightArmourDefense = midnightArmourDefense;
    }

    @Override
    public void use(Game game) {

    }

    @Override
    public BattleStatistics applyBuff(BattleStatistics origin) {
        return origin;
    }

    public int getMidnightArmourAttack() {
        return midnightArmourAttack;
    }

    public int getMidnightArmourDefense() {
        return midnightArmourDefense;
    }

    @Override
    public int getDurability() {
        return 100;
    }

}
