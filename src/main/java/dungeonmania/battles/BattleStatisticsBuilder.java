package dungeonmania.battles;

public class BattleStatisticsBuilder {
    private double health;
    private double attack;
    private double defence;
    private double magnifier;
    private double reducer;
    private boolean invincible;
    private boolean enabled;

    public BattleStatisticsBuilder() {
        this.health = 0.0;
        this.attack = 0.0;
        this.defence = 0.0;
        this.magnifier = 0.0;
        this.reducer = 0.0;

        this.invincible = false;
        this.enabled = true;
    }

    public BattleStatisticsBuilder setHealth(double health) {
        this.health = health;
        return this;
    }

    public BattleStatisticsBuilder setAttack(double attack) {
        this.attack = attack;
        return this;
    }

    public BattleStatisticsBuilder setDefence(double defence) {
        this.defence = defence;
        return this;
    }

    public BattleStatisticsBuilder setMagnifier(double magnifier) {
        this.magnifier = magnifier;
        return this;
    }

    public BattleStatisticsBuilder setReducer(double reducer) {
        this.reducer = reducer;
        return this;
    }

    public BattleStatisticsBuilder setInvincible(boolean invincible) {
        this.invincible = invincible;
        return this;
    }

    public BattleStatisticsBuilder setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public BattleStatistics build() {
        return new BattleStatistics(health, attack, defence, magnifier, reducer, invincible, enabled);
    }
}
