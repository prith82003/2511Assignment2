package dungeonmania.mvp;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SunStoneTest {
    @Test
    @Tag("16-1")
    @DisplayName("Test sunstone is can be picked up")
    public void testSunStoneBuildable() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_sunStoneTest", "c_sunStoneTest");

        assertEquals(1, TestUtils.getEntities(res, "sun_stone").size());
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());

        // Pick up SunStone
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        assertEquals(0, TestUtils.getEntities(res, "sun_stone").size());
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());
    }

    @Test
    @Tag("16-2")
    @DisplayName("Test sunstone can open door")
    public void testSunStoneOpensDoor() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_sunStoneTest", "c_sunStoneTest");

        // pick up key
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);

        // open door
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);

        // player no longer has a key but can move freely through door
        assertEquals(0, TestUtils.getInventory(res, "key").size());

        Position pos = TestUtils.getEntities(res, "player").get(0).getPosition();
        res = dmc.tick(Direction.RIGHT);
        assertNotEquals(pos, TestUtils.getEntities(res, "player").get(0).getPosition());
        pos = TestUtils.getEntities(res, "player").get(0).getPosition();
        res = dmc.tick(Direction.LEFT);
        assertNotEquals(pos, TestUtils.getEntities(res, "player").get(0).getPosition());
        pos = TestUtils.getEntities(res, "player").get(0).getPosition();
        res = dmc.tick(Direction.LEFT);
        assertNotEquals(pos, TestUtils.getEntities(res, "player").get(0).getPosition());
    }

    @Test
    @Tag("16-3")
    @DisplayName("Test sunstone cant open second door with different key")
    public void testSunStoneCantOpenDoor() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_sunStoneTest", "c_sunStoneTest");

        // pick up key
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);

        // walk to door
        res = dmc.tick(Direction.UP);

        Position pos = TestUtils.getEntities(res, "player").get(0).getPosition();
        res = dmc.tick(Direction.UP);
        assertEquals(pos, TestUtils.getEntities(res, "player").get(0).getPosition());
        pos = TestUtils.getEntities(res, "player").get(0).getPosition();
        res = dmc.tick(Direction.UP);
        assertEquals(pos, TestUtils.getEntities(res, "player").get(0).getPosition());
    }
}
