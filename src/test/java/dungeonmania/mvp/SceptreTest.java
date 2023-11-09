package dungeonmania.mvp;

import dungeonmania.DungeonManiaController;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SceptreTest {
    @Test
    @Tag("17-1")
    @DisplayName("Test sceptre can be built")
    public void testSceptreBuilable() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_sceptreTest", "c_sceptreTest");

        // Test cant build
        assertThrows(InvalidActionException.class, () -> dmc.build("sceptre"));

        // Pick up buildable items
        res = dmc.tick(Direction.DOWN);
        assertEquals(6, TestUtils.getEntities(res).size());
        res = dmc.tick(Direction.DOWN);
        assertEquals(5, TestUtils.getEntities(res).size());
        res = dmc.tick(Direction.DOWN);
        assertEquals(4, TestUtils.getEntities(res).size());

        // Build Sceptre
        assertEquals(0, TestUtils.getInventory(res, "sceptre").size());
        res = assertDoesNotThrow(() -> dmc.build("sceptre"));
        assertEquals(1, TestUtils.getInventory(res, "sceptre").size());
    }

    @Test
    @Tag("17-2")
    @DisplayName("Test sceptre can be built up with only wood and sunstone")
    public void testSceptreBuilableWoodSunStone() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_sceptreTest", "c_sceptreTest");

        // Test cant build
        assertThrows(InvalidActionException.class, () -> dmc.build("sceptre"));

        // Pick up buildable items
        res = dmc.tick(Direction.UP);
        assertEquals(6, TestUtils.getEntities(res).size());
        res = dmc.tick(Direction.UP);
        assertEquals(5, TestUtils.getEntities(res).size());

        // Build Sceptre
        assertEquals(0, TestUtils.getInventory(res, "sceptre").size());
        res = assertDoesNotThrow(() -> dmc.build("sceptre"));
        assertEquals(1, TestUtils.getInventory(res, "sceptre").size());
    }

    @Test
    @Tag("17-3")
    @DisplayName("Test sceptre can mindControls mercenary and durability")
    public void testSceptreMindControlsMercenary() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_sceptreTest", "c_sceptreTest");

        String mercId = TestUtils.getEntitiesStream(res, "mercenary").findFirst().get().getId();

        // Test cant build
        assertThrows(InvalidActionException.class, () -> dmc.build("sceptre"));

        // Pick up buildable items
        res = dmc.tick(Direction.UP);
        assertEquals(6, TestUtils.getEntities(res).size());
        res = dmc.tick(Direction.UP);
        assertEquals(5, TestUtils.getEntities(res).size());

        // Attempt mind control
        res = assertDoesNotThrow(() -> dmc.build("sceptre"));
        res = assertDoesNotThrow(() -> dmc.interact(mercId));
        assertEquals(0, TestUtils.getInventory(res, "sceptre").size());

        // Durability of 1 so should remove
        assertTrue(res.getInventory().isEmpty());
    }
}
