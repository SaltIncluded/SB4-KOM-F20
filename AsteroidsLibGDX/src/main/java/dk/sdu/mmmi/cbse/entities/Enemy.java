/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dk.sdu.mmmi.cbse.entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import dk.sdu.mmmi.cbse.main.Game;

/**
 *
 * @author Casper
 */
public class Enemy extends SpaceObject {
    private float maxSpeed;
    private float acceleration;
    private float deceleration;
    
    public Enemy() {
        float randomNumber = MathUtils.random();
        if (randomNumber < 0.5) {//right or left
            y = Game.HEIGHT * MathUtils.random();
            x = randomNumber < 0.25 ? Game.WIDTH : 0;
        } else {//up or down
            x = Game.WIDTH * MathUtils.random();
            y = randomNumber < 0.75 ? Game.HEIGHT : 0;
        }
        
        maxSpeed = 100;
        acceleration = 200;
        deceleration = 10;

        shapex = new float[6];
        shapey = new float[6];

        radians = 3.1415f / 2;
        rotationSpeed = 3;
        
    }
    
    private void setShape() {
        shapex[0] = x + MathUtils.cos(radians) * 5;
        shapey[0] = y + MathUtils.sin(radians) * 5;
        
        shapex[1] = x + MathUtils.cos(radians - 4 * 3.1415f / 8) * 8;
        shapey[1] = y + MathUtils.sin(radians - 4 * 3.1145f / 8) * 8;
        
        shapex[2] = x + MathUtils.cos(radians - 4 * 3.1415f / 5) * 3;
        shapey[2] = y + MathUtils.sin(radians - 4 * 3.1415f / 5) * 3;

        shapex[3] = x + MathUtils.cos(radians + 3.1415f);
        shapey[3] = y + MathUtils.sin(radians + 3.1415f);
        
        shapex[4] = x + MathUtils.cos(radians + 4 * 3.1415f / 5) * 3;
        shapey[4] = y + MathUtils.sin(radians + 4 * 3.1415f / 5) * 3;
        
        shapex[5] = x + MathUtils.cos(radians + 4 * 3.1415f / 8) * 8;
        shapey[5] = y + MathUtils.sin(radians + 4 * 3.1415f / 8) * 8;
    }
    
    public void update(float dt) {

        float randomNumber = MathUtils.random();
        
        if (randomNumber < 0.20) { // turning left
            radians += rotationSpeed * dt;
        } else if (randomNumber < 0.40) { // turning right
            radians -= rotationSpeed * dt;
        } else { // accelerating
            dx += MathUtils.cos(radians) * acceleration * dt;
            dy += MathUtils.sin(radians) * acceleration * dt;
        }

        // deceleration
        float vec = (float) Math.sqrt(dx * dx + dy * dy);
        if (vec > 0) {
            dx -= (dx / vec) * deceleration * dt;
            dy -= (dy / vec) * deceleration * dt;
        }
        if (vec > maxSpeed) {
            dx = (dx / vec) * maxSpeed;
            dy = (dy / vec) * maxSpeed;
        }

        // set position
        x += dx * dt;
        y += dy * dt;

        // set shape
        setShape();

        // screen wrap
        wrap();

    }
    
    public void draw(ShapeRenderer sr) {

        sr.setColor(1, 1, 1, 1);

        sr.begin(ShapeRenderer.ShapeType.Line);

        for (int i = 0, j = shapex.length - 1;
                i < shapex.length;
                j = i++) {

            sr.line(shapex[i], shapey[i], shapex[j], shapey[j]);

        }

        sr.end();

    }
    
}
