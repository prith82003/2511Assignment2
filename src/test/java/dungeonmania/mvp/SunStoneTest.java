package dungeonmania.mvp;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import dungeonmania.exceptions.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SunStoneTest {
    @Test
    public void testSunStoneCreationAndCollectableAndTreasureGoal() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_sunStoneTest_createCollectTreasureGoal",
                "c_sunStoneTest_createCollectTreasureGoal");

        assertEquals(":treasure", TestUtils.getGoals(res));
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());
        assertEquals(0, TestUtils.getInventory(res, "treasure").size());

        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());

        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "treasure").size());

        assertEquals("", TestUtils.getGoals(res));
    }

    @Test
    public void testSunStoneUsageOpeningDoors() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_sunStoneTest_openDoors", "c_sunStoneTest_openDoors");

        assertEquals(":exit", TestUtils.getGoals(res));
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());

        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());

        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());
        Position pos = TestUtils.getEntities(res, "player").get(0).getPosition();

        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());
        assertNotEquals(pos, TestUtils.getEntities(res, "player").get(0).getPosition());

        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());

        assertEquals("", TestUtils.getGoals(res));
    }

    @Test
    public void testSunStoneDoorPriority() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_sunStoneTest_openDoorsPriority", "c_sunStoneTest_openDoors");

        assertEquals(":exit", TestUtils.getGoals(res));
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());
        assertEquals(0, TestUtils.getInventory(res, "key").size());

        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());
        assertEquals(0, TestUtils.getInventory(res, "key").size());

        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());
        assertEquals(1, TestUtils.getInventory(res, "key").size());

        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());
        assertEquals(0, TestUtils.getInventory(res, "key").size());
        Position pos = TestUtils.getEntities(res, "player").get(0).getPosition();

        res = dmc.tick(Direction.RIGHT);
        assertNotEquals(pos, TestUtils.getEntities(res, "player").get(0).getPosition());

        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());

        assertEquals("", TestUtils.getGoals(res));
    }

    @Test
    public void testSunStonePriorityCrafting() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_sunStoneTest_priorityCrafting", "c_sunStoneTest_openDoors");

        assertEquals(":exit", TestUtils.getGoals(res));
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());
        assertEquals(0, TestUtils.getInventory(res, "wood").size());
        assertEquals(0, TestUtils.getInventory(res, "treasure").size());
        assertEquals(0, TestUtils.getInventory(res, "key").size());

        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "wood").size());
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());
        assertEquals(0, TestUtils.getInventory(res, "treasure").size());
        assertEquals(0, TestUtils.getInventory(res, "key").size());

        res = dmc.tick(Direction.RIGHT);
        assertEquals(2, TestUtils.getInventory(res, "wood").size());
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());
        assertEquals(0, TestUtils.getInventory(res, "treasure").size());
        assertEquals(0, TestUtils.getInventory(res, "key").size());

        res = dmc.tick(Direction.RIGHT);
        assertEquals(2, TestUtils.getInventory(res, "wood").size());
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());
        assertEquals(0, TestUtils.getInventory(res, "treasure").size());
        assertEquals(0, TestUtils.getInventory(res, "key").size());

        res = dmc.tick(Direction.RIGHT);
        assertEquals(2, TestUtils.getInventory(res, "wood").size());
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());
        assertEquals(1, TestUtils.getInventory(res, "treasure").size());
        assertEquals(0, TestUtils.getInventory(res, "key").size());

        res = dmc.tick(Direction.RIGHT);
        assertEquals(2, TestUtils.getInventory(res, "wood").size());
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());
        assertEquals(1, TestUtils.getInventory(res, "treasure").size());
        assertEquals(1, TestUtils.getInventory(res, "key").size());

        res = dmc.tick(Direction.RIGHT);
        res = assertDoesNotThrow(() -> dmc.build("shield"));

        assertEquals(1, TestUtils.getInventory(res, "shield").size());
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());
        assertEquals(1, TestUtils.getInventory(res, "key").size());
        assertEquals(0, TestUtils.getInventory(res, "treasure").size());
        assertEquals(0, TestUtils.getInventory(res, "wood").size());

        res = dmc.tick(Direction.RIGHT);
        assertEquals("", TestUtils.getGoals(res));
    }

    @Test
    public void testSunStoneCraftingReplacement() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_sunStoneTest_craftingReplacementSunStone", "c_sunStoneTest_openDoors");

        assertEquals(":exit", TestUtils.getGoals(res));
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());
        assertEquals(0, TestUtils.getInventory(res, "wood").size());

        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "wood").size());
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());

        res = dmc.tick(Direction.RIGHT);
        assertEquals(2, TestUtils.getInventory(res, "wood").size());
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());

        res = dmc.tick(Direction.RIGHT);
        assertEquals(2, TestUtils.getInventory(res, "wood").size());
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());

        res = dmc.tick(Direction.RIGHT);
        res = assertDoesNotThrow(() -> dmc.build("shield"));

        assertEquals(1, TestUtils.getInventory(res, "shield").size());
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());
        assertEquals(0, TestUtils.getInventory(res, "wood").size());

        res = dmc.tick(Direction.RIGHT);
        assertEquals("", TestUtils.getGoals(res));
    }

    @Test
    public void testBribeDoesNotWork() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_sunStoneTest_cannotBribeMercenary", "c_sunStoneTest_cannotBribeMercenary");
        String mercenaryId = TestUtils.getEntitiesStream(res, "mercenary").findFirst().get().getId();

        assertEquals(":treasure", TestUtils.getGoals(res));
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());
        assertEquals(0, TestUtils.getInventory(res, "treasyre").size());

        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());
        assertEquals(0, TestUtils.getInventory(res, "treasyre").size());

        assertThrows(InvalidActionException.class, () -> dmc.interact(mercenaryId));
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());
        assertEquals(0, TestUtils.getInventory(res, "treasyre").size());

        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());
        assertEquals(0, TestUtils.getInventory(res, "treasyre").size());

        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());
        assertEquals(0, TestUtils.getInventory(res, "treasyre").size());

        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());
        assertEquals(1, TestUtils.getInventory(res, "treasure").size());
        assertEquals("", TestUtils.getGoals(res));
    }
}
