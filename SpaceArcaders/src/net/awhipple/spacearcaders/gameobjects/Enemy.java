/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.awhipple.spacearcaders.gameobjects;

import net.awhipple.spacearcaders.ai.AI;
import net.awhipple.spacearcaders.ai.actions.AIFire;
import net.awhipple.spacearcaders.ai.actions.AIMoveRandom;
import net.awhipple.spacearcaders.ai.actions.AIWait;
import net.awhipple.spacearcaders.ai.actions.AIMoveTo;
import net.awhipple.spacearcaders.utils.GameState;
import org.newdawn.slick.Image;

/**
 *
 * @author Aaron
 */
public class Enemy implements Actor {

    private Image image;
    private float x, y,numshots;
        
    private AI ai;
    
    public Enemy(float x, float y, Image image) {
        this.x = x;
        this.y = y;
        
        this.image = image;
        
        ai = new AI();
        
        ai.addAIAction(new AIMoveTo(800,350,130));
        ai.addAIAction(new AIWait(2f));
        
        ai.addAILoopAction(new AIMoveRandom(1600, 900, 300));
        ai.addAILoopAction(new AIFire());  
    }
    
    @Override
    public void draw() {
        image.draw(x-image.getWidth()/2, y-image.getHeight()/2);
    }

    @Override
    public void update(GameState gs) {
numshots = 0;
        ai.execute(this, gs);
    }
    
    public void fire(GameState gs) {
        gs.queueNewActor(new Laser(x, y, gs.getImage("laser")));
numshots++;System.out.println(numshots);
    }
    
    public float getX() { return x; }
    public float getY() { return y; }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }
}
