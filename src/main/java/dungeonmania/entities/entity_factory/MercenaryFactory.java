package dungeonmania.entities.entity_factory;

import org.json.JSONObject;

import dungeonmania.entities.Entity;
import dungeonmania.entities.enemies.Mercenary;
import dungeonmania.util.Position;

class MercenaryFactory implements IFactory {
    @Override
    public Entity constructEntity(Position pos, JSONObject config, JSONObject jsonEntity) {
        double mercenaryHealth = config.optDouble("mercenary_health", Mercenary.DEFAULT_HEALTH);
        double mercenaryAttack = config.optDouble("mercenary_attack", Mercenary.DEFAULT_ATTACK);
        double allyAttack = config.optDouble("ally_attack", Mercenary.DEFAULT_HEALTH);
        double allyDefence = config.optDouble("ally_defence", Mercenary.DEFAULT_ATTACK);
        int mercenaryBribeAmount = config.optInt("bribe_amount", Mercenary.DEFAULT_BRIBE_AMOUNT);
        int mercenaryBribeRadius = config.optInt("bribe_radius", Mercenary.DEFAULT_BRIBE_RADIUS);
        return new Mercenary(pos, mercenaryHealth, mercenaryAttack, mercenaryBribeAmount, mercenaryBribeRadius,
                allyAttack, allyDefence);
    }
}
