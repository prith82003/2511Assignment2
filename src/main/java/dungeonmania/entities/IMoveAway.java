package dungeonmania.entities;

import dungeonmania.map.GameMap;

public interface IMoveAway {
    public void onMovedAway(GameMap map, Entity entity);
}
