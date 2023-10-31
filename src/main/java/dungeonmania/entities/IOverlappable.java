package dungeonmania.entities;

import dungeonmania.map.GameMap;

public interface IOverlappable {
    public void onOverlap(GameMap map, Entity entity);
}
