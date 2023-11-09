package dungeonmania.entities.entity_factory;

import org.json.JSONObject;

import dungeonmania.entities.Entity;
import dungeonmania.entities.collectables.potions.InvincibilityPotion;
import dungeonmania.util.Position;

class InvincibilityPotionFactory implements IFactory {
    @Override
    public Entity constructEntity(Position pos, JSONObject config, JSONObject jsonEntity) {
        int invincibilityPotionDuration = config.optInt("invincibility_potion_duration",
                InvincibilityPotion.DEFAULT_DURATION);
        return new InvincibilityPotion(pos, invincibilityPotionDuration);
    }
}
