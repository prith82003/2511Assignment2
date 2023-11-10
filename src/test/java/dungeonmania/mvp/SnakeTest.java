package dungeonmania.mvp;

import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class SnakeTest {
    @Test
    public void testSnakeCreation() {
        DungeonManiaController dmc = new DungeonManiaController();
        var res = dmc.newGame("d_snakeTest_basicSnake", "c_snakeTest_weakSnake");

        List<EntityResponse> entities = res.getEntities();
        assertEquals(1, TestUtils.countEntityOfType(entities, "snake_head"));
        assertEquals(0, TestUtils.countEntityOfType(entities, "snake_body"));
    }

    @Test
    public void testSnakePathfindAndGrowth() {
        DungeonManiaController dmc = new DungeonManiaController();
        var res = dmc.newGame("d_snakeTest_basicSnake", "c_snakeTest_weakSnake");

        List<EntityResponse> entities = res.getEntities();
        assertEquals(1, TestUtils.countEntityOfType(entities, "snake_head"));
        assertEquals(0, TestUtils.countEntityOfType(entities, "snake_body"));

        for (int i = 0; i < 4; i++)
            res = dmc.tick(Direction.DOWN);

        entities = res.getEntities();
        assertEquals(1, TestUtils.countEntityOfType(entities, "snake_head"));
        assertEquals(1, TestUtils.countEntityOfType(entities, "snake_body"));

        for (int i = 0; i < 4; i++)
            res = dmc.tick(Direction.DOWN);

        entities = res.getEntities();
        assertEquals(1, TestUtils.countEntityOfType(entities, "snake_head"));
        assertEquals(2, TestUtils.countEntityOfType(entities, "snake_body"));
    }

    @Test
    public void testSnakeBattle() {
        DungeonManiaController dmc = new DungeonManiaController();
        var res = dmc.newGame("d_snakeTest_longSnake", "c_snakeTest_weakSnake");

        List<EntityResponse> entities = res.getEntities();
        assertEquals(1, TestUtils.countEntityOfType(entities, "snake_head"));
        assertEquals(0, TestUtils.countEntityOfType(entities, "snake_body"));

        for (int i = 0; i < 6; i++)
            res = dmc.tick(Direction.LEFT);

        for (int i = 0; i < 27; i++)
            res = dmc.tick(Direction.DOWN);

        entities = res.getEntities();
        assertEquals(1, TestUtils.countEntityOfType(entities, "snake_head"));
        assertEquals(7, TestUtils.countEntityOfType(entities, "snake_body"));

        res = dmc.tick(Direction.LEFT);
        entities = res.getEntities();

        assertEquals(1, TestUtils.countEntityOfType(entities, "snake_head"));
        assertEquals(1, TestUtils.countEntityOfType(entities, "snake_body"));
    }

    @Test
    public void testSnakeInvisibility() {
        DungeonManiaController dmc = new DungeonManiaController();
        var res = dmc.newGame("d_snakeTest_basicSnake", "c_snakeTest_weakSnake");

        List<EntityResponse> entities = res.getEntities();
        assertEquals(1, TestUtils.countEntityOfType(entities, "snake_head"));
        assertEquals(0, TestUtils.countEntityOfType(entities, "snake_body"));

        for (int i = 0; i < 11; i++)
            res = dmc.tick(Direction.DOWN);

        var snakeHead = TestUtils.getEntities(res, "snake_head").get(0);
        var position = snakeHead.getPosition();

        // Check if snake in wall
        assertEquals(new Position(7, 7), position);
    }

    @Test
    public void testSnakeInvincibility() {
        DungeonManiaController dmc = new DungeonManiaController();
        var res = dmc.newGame("d_snakeTest_invincibleSnake", "c_snakeTest_weakSnake");

        List<EntityResponse> entities = res.getEntities();
        assertEquals(1, TestUtils.countEntityOfType(entities, "snake_head"));
        assertEquals(0, TestUtils.countEntityOfType(entities, "snake_body"));

        for (int i = 0; i < 6; i++)
            res = dmc.tick(Direction.LEFT);

        for (int i = 0; i < 27; i++)
            res = dmc.tick(Direction.DOWN);

        entities = res.getEntities();
        assertEquals(1, TestUtils.countEntityOfType(entities, "snake_head"));
        assertEquals(7, TestUtils.countEntityOfType(entities, "snake_body"));

        res = dmc.tick(Direction.LEFT);
        entities = res.getEntities();

        assertEquals(2, TestUtils.countEntityOfType(entities, "snake_head"));
        assertEquals(6, TestUtils.countEntityOfType(entities, "snake_body"));
    }
}
