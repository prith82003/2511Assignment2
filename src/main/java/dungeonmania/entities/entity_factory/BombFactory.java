package dungeonmania.entities.entity_factory;

import org.json.JSONObject;

import dungeonmania.entities.Entity;
import dungeonmania.entities.collectables.Bomb;
import dungeonmania.util.Position;

public class BombFactory implements IFactory {
    @Override
    public Entity constructEntity(Position pos, JSONObject config, JSONObject jsonEntity) {
        int bombRadius = config.optInt("bomb_radius", Bomb.DEFAULT_RADIUS);
        return new Bomb(pos, bombRadius);
    }
}
