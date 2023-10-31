package dungeonmania.goals;

import org.json.JSONObject;

public interface IFactory {
    public Goal createGoal(JSONObject jsonGoal, JSONObject config);
}
