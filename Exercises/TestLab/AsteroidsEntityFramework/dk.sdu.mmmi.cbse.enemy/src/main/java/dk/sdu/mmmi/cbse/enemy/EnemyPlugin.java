package dk.sdu.mmmi.cbse.enemy;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.commonenemy.Enemy;

/**
 *
 * @author Casper
 */
public class EnemyPlugin implements IGamePluginService {

    private Entity enemy;

    public EnemyPlugin() {
    }

    @Override
    public void start(GameData gameData, World world) {
        
        // Add entities to the world
        this.enemy = createEnemyShip(gameData);
        world.addEntity(this.enemy);
    }

    private Entity createEnemyShip(GameData gameData) {

        float deacceleration = 10;
        float acceleration = 200;
        float maxSpeed = 300;
        float rotationSpeed = 5;
        float x;
        float y;
        //Spawn at random point
        float randomNumber = (float)(Math.random());
        if (randomNumber < 0.5) {//right or left
            y = gameData.getDisplayHeight() * (float)(Math.random());
            x = randomNumber < 0.25 ? gameData.getDisplayWidth() : 0;
        } else {//up or down
            x = gameData.getDisplayWidth() * (float)(Math.random());
            y = randomNumber < 0.75 ? gameData.getDisplayHeight() : 0;
        }
        float radians = 3.1415f / 2;
        
        Entity enemyShip = new Enemy();
        enemyShip.add(new MovingPart(deacceleration, acceleration, maxSpeed, rotationSpeed));
        enemyShip.add(new PositionPart(x, y, radians));
        enemyShip.add(new LifePart(6, 69));
        enemyShip.setRadius(8f);
        
        return enemyShip;
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        world.removeEntity(this.enemy);
    }

}
