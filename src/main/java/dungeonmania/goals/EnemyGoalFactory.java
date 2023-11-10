package dungeonmania.goals;

import org.json.JSONObject;

public class EnemyGoalFactory implements IFactory {
    @Override
    public Goal createGoal(JSONObject jsonGoal, JSONObject config) {
        int target = config.optInt("enemy_goal", 1);
        return new EnemyGoal(target);
    }
}
