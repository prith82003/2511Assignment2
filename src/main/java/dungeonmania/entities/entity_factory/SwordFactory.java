package dungeonmania.entities.entity_factory;

import org.json.JSONObject;

import dungeonmania.entities.Entity;
import dungeonmania.entities.collectables.Sword;
import dungeonmania.util.Position;

class SwordFactory implements IFactory {
    @Override
    public Entity constructEntity(Position pos, JSONObject config, JSONObject jsonEntity) {
        double swordAttack = config.optDouble("sword_attack", Sword.DEFAULT_ATTACK);
        int swordDurability = config.optInt("sword_durability", Sword.DEFAULT_DURABILITY);
        return new Sword(pos, swordAttack, swordDurability);
    }
}
