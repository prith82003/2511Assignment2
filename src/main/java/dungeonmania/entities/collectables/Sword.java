package dungeonmania.entities.collectables;

import dungeonmania.Game;
import dungeonmania.battles.BattleStatistics;
import dungeonmania.battles.BattleStatisticsBuilder;
import dungeonmania.entities.BattleItem;
import dungeonmania.util.Position;

public class Sword extends Collectable implements BattleItem {
    public static final double DEFAULT_ATTACK = 1;
    public static final double DEFAULT_ATTACK_SCALE_FACTOR = 1;
    public static final int DEFAULT_DURABILITY = 5;
    public static final double DEFAULT_DEFENCE = 0;
    public static final double DEFAULT_DEFENCE_SCALE_FACTOR = 1;

    private static final double BUFF_SWORD_MAGNIFIER = 1;
    private static final double BUFF_SWORD_REDUCER = 1;

    private int durability;
    private double attack;

    public Sword(Position position, double attack, int durability) {
        super(position);
        this.attack = attack;
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
        builder.setAttack(attack).setMagnifier(BUFF_SWORD_MAGNIFIER).setReducer(BUFF_SWORD_REDUCER);
        return BattleStatistics.applyBuff(origin, builder.build());
    }

    @Override
    public int getDurability() {
        return durability;
    }
}
