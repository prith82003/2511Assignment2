package dungeonmania.entities.inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.Map;

import dungeonmania.entities.BattleItem;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Player;
import dungeonmania.entities.buildables.Bow;
import dungeonmania.entities.buildables.BowBuildable;
import dungeonmania.entities.buildables.BuildableRecipe;
import dungeonmania.entities.buildables.MidnightArmourBuildable;
import dungeonmania.entities.buildables.ShieldBuildable;
import dungeonmania.entities.buildables.SceptreBuildable;
import dungeonmania.entities.collectables.Sword;
import dungeonmania.entities.entity_factory.EntityFactory;
import dungeonmania.util.Position;

public class Inventory {
    private List<InventoryItem> items = new ArrayList<>();

    private static Map<String, BuildableRecipe> buildables = new HashMap<>() {
        {
            put("bow", new BowBuildable());
            put("shield", new ShieldBuildable());
            put("sceptre", new SceptreBuildable());
            put("midnightArmour", new MidnightArmourBuildable());
        }
    };

    public boolean add(InventoryItem item) {
        items.add(item);

        return true;
    }

    public void remove(InventoryItem item) {
        items.remove(item);
    }

    public void removeType(Class<? extends InventoryItem> itemType, int amount) {
        for (int i = 0; i < items.size(); i++) {
            if (itemType.isInstance(items.get(i))) {
                items.remove(i);
                i--;
                amount--;
                if (amount == 0)
                    return;
            }
        }
    }

    public List<String> getBuildables() {
        List<String> result = new ArrayList<>();

        for (String entity : buildables.keySet()) {
            if (buildables.get(entity).canConstruct(this, false))
                result.add(entity);
        }
        return result;
    }

    public InventoryItem checkBuildCriteria(Player p, boolean remove, String entity, EntityFactory factory) {
        if (buildables.get(entity).canConstruct(p.getInventory(), remove))
            return (InventoryItem) factory.createEntity(entity, Position.ZERO);

        return null;
    }

    public <T extends InventoryItem> T getFirst(Class<T> itemType) {
        for (InventoryItem item : items)
            if (itemType.isInstance(item))
                return itemType.cast(item);
        return null;
    }

    public InventoryItem returnFirst(Class<?> itemType) {
        for (InventoryItem item : items)
            if (itemType.isInstance(item))
                return item;
        return null;
    }

    public <T extends InventoryItem> int count(Class<T> itemType) {
        int count = 0;
        for (InventoryItem item : items)
            if (itemType.isInstance(item))
                count++;
        return count;
    }

    public Entity getEntity(String itemUsedId) {
        for (InventoryItem item : items)
            if (((Entity) item).getId().equals(itemUsedId))
                return (Entity) item;
        return null;
    }

    public List<Entity> getEntities() {
        return items.stream().map(Entity.class::cast).collect(Collectors.toList());
    }

    public <T> List<T> getEntities(Class<T> clz) {
        return items.stream().filter(clz::isInstance).map(clz::cast).collect(Collectors.toList());
    }

    public boolean hasWeapon() {
        return getFirst(Sword.class) != null || getFirst(Bow.class) != null;
    }

    public BattleItem getWeapon() {
        BattleItem weapon = getFirst(Sword.class);
        if (weapon == null)
            return getFirst(Bow.class);
        return weapon;
    }

}
