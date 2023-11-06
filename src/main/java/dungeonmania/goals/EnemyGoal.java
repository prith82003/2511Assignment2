package dungeonmania.goals;

import dungeonmania.Game;
import dungeonmania.entities.enemies.ZombieToastSpawner;

public class EnemyGoal implements Goal {
    private int target;

    public EnemyGoal(int target) {
        this.target = target;
    }

    @Override
    public boolean achieved(Game game) {
        int numSpawners = game.getMap().getEntities(ZombieToastSpawner.class).size();
        return game.getNumEnemiesKilled() >= target && numSpawners == 0;
    }

    @Override
    public String toString(Game game) {
        return (this.achieved(game) ? "" : ":enemy");
    }
}
