package dungeonmania.entities.entity_factory;

import org.json.JSONObject;

import dungeonmania.entities.Entity;
import dungeonmania.entities.collectables.SunStone;
import dungeonmania.util.Position;

public class SunStoneFactory implements IFactory {
    @Override
    public Entity constructEntity(Position pos, JSONObject config, JSONObject jsonEntity) {
        return new SunStone(pos);
    }
}
