package dungeonmania.mvp;

import dungeonmania.DungeonManiaController;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.RoundResponse;
import dungeonmania.util.Direction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class MidnightArmourTest {
    @Test
    @Tag("18-1")
    @DisplayName("Test MidnightArmour can be built")
    public void testMidnightArmourBuilt() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_midnightArmourTest", "c_midnightArmourTest");

        // Test cant build
        assertThrows(InvalidActionException.class, () -> dmc.build("midnight_armour"));

        // Pick up buildable items
        assertEquals(4, TestUtils.getEntities(res).size());
        res = dmc.tick(Direction.DOWN);
        assertEquals(3, TestUtils.getEntities(res).size());
        res = dmc.tick(Direction.DOWN);
        assertEquals(2, TestUtils.getEntities(res).size());

        // Build Armour
        assertEquals(0, TestUtils.getInventory(res, "midnight_armour").size());
        res = assertDoesNotThrow(() -> dmc.build("midnight_armour"));
        assertEquals(1, TestUtils.getInventory(res, "midnight_armour").size());
    }

    @Test
    @Tag("18-2")
    @DisplayName("Test MidnightArmour increases attack damage")
    public void testMidnightArmourIncreaseDamage() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        String config = "c_midnightArmourTest";
        DungeonResponse res = dmc.newGame("d_midnightArmourTest", config);

        // Test cant build
        assertThrows(InvalidActionException.class, () -> dmc.build("midnight_armour"));

        // Pick up buildable items
        assertEquals(4, TestUtils.getEntities(res).size());
        res = dmc.tick(Direction.DOWN);
        assertEquals(3, TestUtils.getEntities(res).size());
        res = dmc.tick(Direction.DOWN);

        // Build Armour
        assertEquals(0, TestUtils.getInventory(res, "midnight_armour").size());
        res = assertDoesNotThrow(() -> dmc.build("midnight_armour"));
        assertEquals(1, TestUtils.getInventory(res, "midnight_armour").size());

        // Battle
        res = dmc.tick(Direction.DOWN);
        List<BattleResponse> battles = res.getBattles();
        BattleResponse battle = battles.get(0);
        double playerBaseAttack = Double.parseDouble(TestUtils.getValueFromConfigFile("player_attack", config));
        double midnightArmourAttack = Double
                .parseDouble(TestUtils.getValueFromConfigFile("midnight_armour_attack", config));

        RoundResponse firstRound = battle.getRounds().get(0);

        assertEquals((playerBaseAttack + midnightArmourAttack) / 5, -firstRound.getDeltaEnemyHealth(), 0.001);
    }

    @Test
    @Tag("18-3")
    @DisplayName("Test MidnightArmour cannot be built when zombies present")
    public void testCannotMakeWhenZombiePresent() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_midnightArmourTestZombie", "c_midnightArmourTest");

        // Pick up buildable items
        assertEquals(5, TestUtils.getEntities(res).size());
        res = dmc.tick(Direction.DOWN);
        assertEquals(4, TestUtils.getEntities(res).size());
        res = dmc.tick(Direction.DOWN);

        // Test cant build
        assertThrows(InvalidActionException.class, () -> dmc.build("midnight_armour"));
    }

}
