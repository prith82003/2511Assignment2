package dungeonmania.mvp;

import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class EnemyGoalsTest {
    @Test
    public void testBasicEnemyGoals() {
        DungeonManiaController dmc = new DungeonManiaController();
        var res = dmc.newGame("d_enemyGoalsTest_zombieToasts", "c_enemyGoalsTest_killSingle");

        List<EntityResponse> entities = res.getEntities();
        assertEquals(4, TestUtils.countEntityOfType(entities, "zombie_toast"));

        res = dmc.tick(Direction.LEFT);

        for (int i = 0; i < 5; i++)
            res = dmc.tick(Direction.RIGHT);

        assertEquals("", res.getGoals());
    }

    @Test
    public void testExitAndEnemyGoals() {
        DungeonManiaController dmc = new DungeonManiaController();
        var res = dmc.newGame("d_enemyGoalsTest_exitAndEnemy", "c_enemyGoalsTest_killSingle");

        List<EntityResponse> entities = res.getEntities();
        assertEquals(4, TestUtils.countEntityOfType(entities, "zombie_toast"));

        res = dmc.tick(Direction.LEFT);

        for (int i = 0; i < 5; i++)
            res = dmc.tick(Direction.RIGHT);

        assertEquals("", res.getGoals());

        res = dmc.tick(Direction.RIGHT);
    }

    @Test
    public void testSpawnerGoals() {
        DungeonManiaController dmc = new DungeonManiaController();
        var res = dmc.newGame("d_enemyGoalsTest_spawner", "c_enemyGoalsTest_noSpawning");

        List<EntityResponse> entities = res.getEntities();
        assertEquals(4, TestUtils.countEntityOfType(entities, "zombie_toast"));
        assertEquals(1, TestUtils.countEntityOfType(entities, "zombie_toast_spawner"));

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.LEFT);

        for (int i = 0; i < 2; i++)
            res = dmc.tick(Direction.RIGHT);

        for (int i = 0; i < 3; i++)
            res = dmc.tick(Direction.LEFT);

        String zts = TestUtils.getEntities(res, "zombie_toast_spawner").get(0).getId();
        res = assertDoesNotThrow(() -> dmc.interact(zts));

        assertEquals("", res.getGoals());

        res = dmc.tick(Direction.RIGHT);
    }

    @Test
    public void testSpawnerAndExitGoals() {
        DungeonManiaController dmc = new DungeonManiaController();
        var res = dmc.newGame("d_enemyGoalsTest_spawnerAndExit", "c_enemyGoalsTest_noSpawning");

        List<EntityResponse> entities = res.getEntities();
        assertEquals(4, TestUtils.countEntityOfType(entities, "zombie_toast"));
        assertEquals(1, TestUtils.countEntityOfType(entities, "zombie_toast_spawner"));

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.LEFT);

        for (int i = 0; i < 2; i++)
            res = dmc.tick(Direction.RIGHT);

        for (int i = 0; i < 3; i++)
            res = dmc.tick(Direction.LEFT);

        String zts = TestUtils.getEntities(res, "zombie_toast_spawner").get(0).getId();
        res = assertDoesNotThrow(() -> dmc.interact(zts));

        assertEquals("(:exit AND )", res.getGoals());

        res = dmc.tick(Direction.RIGHT);
    }
}
