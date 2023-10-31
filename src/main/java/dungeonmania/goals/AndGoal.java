package dungeonmania.goals;

import dungeonmania.Game;

public class AndGoal implements Goal {
    private Goal child1;
    private Goal child2;

    public AndGoal(Goal child1, Goal child2) {
        this.child1 = child1;
        this.child2 = child2;
    }

    @Override
    public boolean achieved(Game game) {
        return child1.achieved(game) && child2.achieved(game);
    }

    @Override
    public String toString(Game game) {
        if (this.achieved(game))
            return "";

        return "(" + child1.toString(game) + " AND " + child2.toString(game) + ")";
    }
}
