package dungeonmania.entities.entity_factory;

import org.json.JSONObject;

import dungeonmania.entities.Entity;
import dungeonmania.entities.enemies.SnakeHead;
import dungeonmania.util.Position;

public class SnakeHeadFactory implements IFactory {
    @Override
    public Entity constructEntity(Position pos, JSONObject config, JSONObject jsonEntity) {
        int attack = config.getInt("snake_attack");
        int health = config.getInt("snake_health");
        int arrowBuff = config.getInt("snake_attack_arrow_buff");
        int treasureBuff = config.getInt("snake_health_treasure_buff");
        int keyBuff = config.getInt("snake_health_key_buff");

        return new SnakeHead(pos, attack, health, arrowBuff, treasureBuff, keyBuff);
    }
}
