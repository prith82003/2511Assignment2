package dungeonmania.entities.entity_factory;

import org.json.JSONObject;

import dungeonmania.entities.Entity;
import dungeonmania.entities.Switch;
import dungeonmania.util.Position;

class SwitchFactory implements IFactory {
    @Override
    public Entity constructEntity(Position pos, JSONObject config, JSONObject jsonEntity) {
        return new Switch(pos);
    }
}
