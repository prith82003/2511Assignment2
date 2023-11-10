package dungeonmania.entities.entity_factory;

import org.json.JSONObject;

import dungeonmania.entities.Entity;
import dungeonmania.entities.buildables.MidnightArmour;
import dungeonmania.util.Position;

public class MidnightArmourFactory implements IFactory {
    @Override
    public Entity constructEntity(Position pos, JSONObject config, JSONObject jsonEntity) {
        int midnightArmourAttack = config.optInt("midnight_armour_attack");
        int midnightArmourDefense = config.optInt("midnight_armour_defense");
        return new MidnightArmour(midnightArmourAttack, midnightArmourDefense);
    }
}
