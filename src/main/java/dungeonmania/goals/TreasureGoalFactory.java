package dungeonmania.goals;

import org.json.JSONObject;

public class TreasureGoalFactory implements IFactory {
    @Override
    public Goal createGoal(JSONObject jsonGoal, JSONObject config) {
        int treasureGoal = config.optInt("treasure_goal", 1);
        return new TreasureGoal(treasureGoal);
    }
}
