package dk.sdu.mmmi.cbse.player;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import org.junit.jupiter.api.Test;

import static dk.sdu.mmmi.cbse.common.data.GameKeys.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class PlayerControlSystemTest {
    private GameData gameData;
    private World world;
    private PlayerControlSystem playerControlSystem;

    public PlayerControlSystemTest() {

    }

    private void initPlayerPlugin() {
        this.gameData.setDisplayWidth(500);
        this.gameData.setDisplayHeight(400);

        PlayerPlugin playerPlugin = new PlayerPlugin();
        playerPlugin.start(this.gameData, this.world);
    }

    @Test
    public void testPlayerStartPosition() {
        System.out.println("Running testPlayerStartPosition!");

        this.gameData = new GameData();
        this.world = new World();

        this.initPlayerPlugin();

        PositionPart positionPart = null;

        for (Entity player : world.getEntities(Player.class)) {
            positionPart = player.getPart(PositionPart.class);
        }

        if (positionPart == null) {
            return;
        }

        //Check starting radian
        assertEquals(3.1415f / 2, positionPart.getRadians());

        //Check starting X and Y
        assertEquals(this.gameData.getDisplayWidth() / 2, positionPart.getX());
        assertEquals(this.gameData.getDisplayHeight() / 2, positionPart.getY());
    }

    @Test
    public void testPlayerMovement() {
        System.out.println("Running testPlayerMovement!");

        this.gameData = new GameData();
        this.world = new World();
        PlayerControlSystem playerControlSystem = new PlayerControlSystem();

        this.initPlayerPlugin();

        PositionPart positionPart = null;
        Entity player = null;

        for (Entity entity : world.getEntities(Player.class)) {
            player = entity;
        }

        Float startY = ((PositionPart)(player.getPart(PositionPart.class))).getY();
        //Move forward
        this.gameData.getKeys().setKey(GameKeys.UP, true);
        gameData.setDelta(1f);
        playerControlSystem.process(this.gameData, this.world);

        Float newY = ((PositionPart)(player.getPart(PositionPart.class))).getY();

        assertNotEquals(startY, newY);

        Float startRadian = ((PositionPart)(player.getPart(PositionPart.class))).getRadians();
        //Rotate
        this.gameData.getKeys().setKey(GameKeys.LEFT, true);
        gameData.setDelta(1f);
        playerControlSystem.process(this.gameData, this.world);

        Float newRadian = ((PositionPart)(player.getPart(PositionPart.class))).getRadians();

        assertNotEquals(startRadian, newRadian);
    }
}
