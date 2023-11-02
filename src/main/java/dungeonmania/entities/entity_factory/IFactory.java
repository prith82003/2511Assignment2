package dungeonmania.entities.entity_factory;

import org.json.JSONObject;

import dungeonmania.entities.Entity;
import dungeonmania.util.Position;

public interface IFactory {
    public Entity constructEntity(Position pos, JSONObject config, JSONObject jsonEntity);
}
