package dungeonmania.entities.buildables;

import dungeonmania.Game;
import dungeonmania.battles.BattleStatistics;

public class Sceptre extends Buildable {
    private int durability;
    private int mindControlDuration;

    public Sceptre(int durability, int mindControlDuration) {
        super(null);
        this.durability = durability;
        this.mindControlDuration = mindControlDuration;
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
        return origin;
    }

    @Override
    public int getDurability() {
        return durability;
    }

    public int getMindControlDuration() {
        return mindControlDuration;
    }
}
