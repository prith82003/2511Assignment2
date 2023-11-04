package dungeonmania.entities.entity_factory;

import org.json.JSONObject;

import dungeonmania.entities.Player;
import dungeonmania.util.Position;

class PlayerFactory implements IFactory {
    @Override
    public Player constructEntity(Position pos, JSONObject config, JSONObject jsonEntity) {
        double playerHealth = config.optDouble("player_health", Player.DEFAULT_HEALTH);
        double playerAttack = config.optDouble("player_attack", Player.DEFAULT_ATTACK);
        return new Player(pos, playerHealth, playerAttack);
    }
}
