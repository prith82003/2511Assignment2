package dungeonmania.entities.entity_factory;

import org.json.JSONObject;

import dungeonmania.entities.Entity;
import dungeonmania.entities.collectables.Wood;
import dungeonmania.util.Position;

class WoodFactory implements IFactory {
    @Override
    public Entity constructEntity(Position pos, JSONObject config, JSONObject jsonEntity) {
        return new Wood(pos);
    }
}
