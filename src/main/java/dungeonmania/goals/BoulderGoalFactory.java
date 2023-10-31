package dungeonmania.goals;

import org.json.JSONObject;

public class BoulderGoalFactory implements IFactory {
    @Override
    public Goal createGoal(JSONObject jsonGoal, JSONObject config) {
        return new BoulderGoal();
    }
}
