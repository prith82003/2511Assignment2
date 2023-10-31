package dungeonmania.goals;

import org.json.JSONObject;

public class ExitGoalFactory implements IFactory {
    @Override
    public Goal createGoal(JSONObject jsonGoal, JSONObject config) {
        return new ExitGoal();
    }
}
