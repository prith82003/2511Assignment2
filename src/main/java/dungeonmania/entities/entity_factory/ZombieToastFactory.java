package dungeonmania.entities.entity_factory;

import org.json.JSONObject;

import dungeonmania.entities.enemies.ZombieToast;
import dungeonmania.util.Position;

class ZombieToastFactory implements IFactory {
    @Override
    public ZombieToast constructEntity(Position pos, JSONObject config, JSONObject jsonEntity) {
        double zombieToastHealth = config.optDouble("zombie_health", ZombieToast.DEFAULT_HEALTH);
        double zombieToastAttack = config.optDouble("zombie_attack", ZombieToast.DEFAULT_ATTACK);
        return new ZombieToast(pos, zombieToastHealth, zombieToastAttack);
    }
}
