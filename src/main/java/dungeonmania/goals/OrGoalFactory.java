package dungeonmania.goals;

import org.json.JSONArray;
import org.json.JSONObject;

public class OrGoalFactory implements IFactory {
    @Override
    public Goal createGoal(JSONObject jsonGoal, JSONObject config) {
        JSONArray subgoals = jsonGoal.getJSONArray("subgoals");
        return new OrGoal(GoalFactory.createGoal(subgoals.getJSONObject(0), config),
                GoalFactory.createGoal(subgoals.getJSONObject(1), config));
    }
}
