package dungeonmania.entities.entity_factory;

import org.json.JSONObject;

import dungeonmania.entities.Entity;
import dungeonmania.entities.buildables.Bow;
import dungeonmania.util.Position;

class BowFactory implements IFactory {
    @Override
    public Entity constructEntity(Position pos, JSONObject config, JSONObject jsonEntity) {
        int bowDurability = config.optInt("bow_durability");
        return new Bow(bowDurability);
    }
}
