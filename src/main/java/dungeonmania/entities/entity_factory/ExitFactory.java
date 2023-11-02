package dungeonmania.entities.entity_factory;

import org.json.JSONObject;

import dungeonmania.entities.Entity;
import dungeonmania.entities.Exit;
import dungeonmania.util.Position;

public class ExitFactory implements IFactory {
    public Entity constructEntity(Position pos, JSONObject config, JSONObject jsonEntity) {
        return new Exit(pos);
    }
}
