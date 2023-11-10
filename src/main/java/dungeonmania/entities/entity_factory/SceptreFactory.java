package dungeonmania.entities.entity_factory;

import org.json.JSONObject;

import dungeonmania.entities.Entity;
import dungeonmania.entities.buildables.Sceptre;
import dungeonmania.util.Position;

public class SceptreFactory implements IFactory {
    @Override
    public Entity constructEntity(Position pos, JSONObject config, JSONObject jsonEntity) {
        int sceptreDurability = config.optInt("sceptre_durability");
        int mindControlDuration = config.optInt("mind_control_duration");
        return new Sceptre(sceptreDurability, mindControlDuration);
    }
}
