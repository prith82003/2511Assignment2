package dungeonmania.entities.entity_factory;

import org.json.JSONObject;

import dungeonmania.entities.enemies.ZombieToastSpawner;
import dungeonmania.util.Position;

public class ZombieToastSpawnerFactory implements IFactory {
    @Override
    public ZombieToastSpawner constructEntity(Position pos, JSONObject config, JSONObject jsonEntity) {
        int zombieSpawnRate = config.optInt("zombie_spawn_interval", ZombieToastSpawner.DEFAULT_SPAWN_INTERVAL);
        return new ZombieToastSpawner(pos, zombieSpawnRate);
    }
}
