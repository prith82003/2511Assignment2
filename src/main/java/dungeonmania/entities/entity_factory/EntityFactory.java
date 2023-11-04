package dungeonmania.entities.entity_factory;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import dungeonmania.Game;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Wall;
import dungeonmania.entities.enemies.Enemy;
import dungeonmania.entities.enemies.Spider;
import dungeonmania.entities.enemies.ZombieToast;
import dungeonmania.entities.enemies.ZombieToastSpawner;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public final class EntityFactory {
    private JSONObject config;
    private final Random ranGen = new Random();

    private static final Map<String, ? extends IFactory> ENTITY_MAP = new HashMap<>() {
        {
            put("player", new PlayerFactory());
            put("zombie_toast", new ZombieToastFactory());
            put("zombie_toast_spawner", new ZombieToastSpawnerFactory());
            put("mercenary", new MercenaryFactory());
            put("wall", new WallFactory());
            put("boulder", new BoulderFactory());
            put("switch", new SwitchFactory());
            put("exit", new ExitFactory());
            put("treasure", new TreasureFactory());
            put("wood", new WoodFactory());
            put("arrow", new ArrowFactory());
            put("bomb", new BombFactory());
            put("invisibility_potion", new InvisibilityPotionFactory());
            put("invincibility_potion", new InvincibilityPotionFactory());
            put("portal", new PortalFactory());
            put("sword", new SwordFactory());
            put("spider", new SpiderFactory());
            put("door", new DoorFactory());
            put("key", new KeyFactory());
            put("bow", new BowFactory());
            put("shield", new ShieldFactory());
        }
    };

    public EntityFactory(JSONObject config) {
        this.config = config;
    }

    public Entity createEntity(JSONObject jsonEntity) {
        String entityType = jsonEntity.getString("type");
        Position pos = new Position(jsonEntity.getInt("x"), jsonEntity.getInt("y"));
        return ENTITY_MAP.get(entityType).constructEntity(pos, config, jsonEntity);
    }

    public Entity createEntity(String entityType, Position pos) {
        return ENTITY_MAP.get(entityType).constructEntity(pos, config, null);
    }

    public void spawnZombie(Game game, ZombieToastSpawner spawner) {
        GameMap map = game.getMap();
        int tick = game.getTick();
        Random randGen = new Random();
        int spawnInterval = config.optInt("zombie_spawn_interval", ZombieToastSpawner.DEFAULT_SPAWN_INTERVAL);
        if (spawnInterval == 0 || (tick + 1) % spawnInterval != 0)
            return;
        List<Position> pos = spawner.getPosition().getCardinallyAdjacentPositions();
        pos = pos.stream().filter(p -> !map.getEntities(p).stream().anyMatch(e -> (e instanceof Wall)))
                .collect(Collectors.toList());
        if (pos.size() == 0)
            return;
        ZombieToast zt = (ZombieToast) createEntity("zombie_toast", pos.get(randGen.nextInt(pos.size())));
        map.addEntity(zt);
        game.register(() -> zt.move(game), Game.AI_MOVEMENT, zt.getId());
    }

    public void spawnSpider(Game game) {
        GameMap map = game.getMap();
        int tick = game.getTick();
        int rate = config.optInt("spider_spawn_interval", 0);
        if (rate == 0 || (tick + 1) % rate != 0)
            return;
        int radius = 20;
        Position player = map.getPlayer().getPosition();

        Spider dummySpider = (Spider) createEntity("spider", new Position(0, 0)); // for checking possible positions

        List<Position> availablePos = new ArrayList<>();
        for (int i = player.getX() - radius; i < player.getX() + radius; i++) {
            for (int j = player.getY() - radius; j < player.getY() + radius; j++) {
                if (Position.calculatePositionBetween(player, new Position(i, j)).magnitude() > radius)
                    continue;
                Position np = new Position(i, j);
                if (!map.canMoveTo(dummySpider, np) || np.equals(player))
                    continue;
                if (map.getEntities(np).stream().anyMatch(e -> e instanceof Enemy))
                    continue;
                availablePos.add(np);
            }
        }
        Position initPosition = availablePos.get(ranGen.nextInt(availablePos.size()));
        Spider spider = (Spider) createEntity("spider", initPosition);
        map.addEntity(spider);
        game.register(() -> spider.move(game), Game.AI_MOVEMENT, spider.getId());
    }

}
