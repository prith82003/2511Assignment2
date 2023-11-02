package dungeonmania.entities.entity_factory;

import org.json.JSONObject;

import dungeonmania.entities.Entity;
import dungeonmania.entities.collectables.potions.InvisibilityPotion;
import dungeonmania.util.Position;

public class InvisibilityPotionFactory implements IFactory {
    @Override
    public Entity constructEntity(Position pos, JSONObject config, JSONObject jsonEntity) {
        int invisibilityPotionDuration = config.optInt("invisibility_potion_duration",
                InvisibilityPotion.DEFAULT_DURATION);
        return new InvisibilityPotion(pos, invisibilityPotionDuration);
    }
}
