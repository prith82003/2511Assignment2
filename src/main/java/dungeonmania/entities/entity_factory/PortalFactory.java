package dungeonmania.entities.entity_factory;

import org.json.JSONObject;

import dungeonmania.entities.ColorCodedType;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Portal;
import dungeonmania.util.Position;

public class PortalFactory implements IFactory {
    @Override
    public Entity constructEntity(Position pos, JSONObject config, JSONObject jsonEntity) {
        return new Portal(pos, ColorCodedType.valueOf(jsonEntity.getString("colour")));
    }
}
