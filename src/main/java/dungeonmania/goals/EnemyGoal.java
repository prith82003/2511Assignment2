package dungeonmania.goals;

import dungeonmania.Game;

public class EnemyGoal implements Goal {
    private int target;

    public EnemyGoal(int target) {
        this.target = target;
    }

    @Override
    public boolean achieved(Game game) {
        return game.getNumEnemiesKilled() >= target;
    }

    @Override
    public String toString(Game game) {
        return (this.achieved(game) ? "" : ":enemy_goal");
    }
}
