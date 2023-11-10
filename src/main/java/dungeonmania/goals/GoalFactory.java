package dungeonmania.goals;

import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class GoalFactory {
    private static Map<String, ? extends IFactory> goals = new HashMap<>() {
        {
            put("AND", new AndGoalFactory());
            put("OR", new OrGoalFactory());
            put("exit", new ExitGoalFactory());
            put("boulders", new BoulderGoalFactory());
            put("treasure", new TreasureGoalFactory());
            put("enemy_goal", new EnemyGoalFactory());
        }
    };

    public static Goal createGoal(JSONObject jsonGoal, JSONObject config) {
        String type = jsonGoal.getString("goal");
        return goals.get(type).createGoal(jsonGoal, config);
    }
}
