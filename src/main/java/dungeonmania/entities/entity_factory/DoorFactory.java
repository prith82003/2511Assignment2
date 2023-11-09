package dungeonmania.entities.entity_factory;

import org.json.JSONObject;

import dungeonmania.entities.Door;
import dungeonmania.entities.Entity;
import dungeonmania.util.Position;

class DoorFactory implements IFactory {
    @Override
    public Entity constructEntity(Position pos, JSONObject config, JSONObject jsonEntity) {
        return new Door(pos, jsonEntity.getInt("key"));
    }
}
