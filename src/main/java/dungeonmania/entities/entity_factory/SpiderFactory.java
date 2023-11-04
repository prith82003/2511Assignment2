package dungeonmania.entities.entity_factory;

import org.json.JSONObject;

import dungeonmania.entities.enemies.Spider;
import dungeonmania.util.Position;

class SpiderFactory implements IFactory {
    @Override
    public Spider constructEntity(Position pos, JSONObject config, JSONObject jsonEntity) {
        double spiderHealth = config.optDouble("spider_health", Spider.DEFAULT_HEALTH);
        double spiderAttack = config.optDouble("spider_attack", Spider.DEFAULT_ATTACK);
        return new Spider(pos, spiderHealth, spiderAttack);
    }
}
