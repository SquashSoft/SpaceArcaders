/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.awhipple.spacearcaders.gameobjects;

import net.awhipple.spacearcaders.ai.AIAction;
import net.awhipple.spacearcaders.ai.DoNothing;
import net.awhipple.spacearcaders.utils.GameState;
import org.newdawn.slick.Image;

/**
 *
 * @author Aaron
 */
public class Enemy implements Actor {

    Image image;
    float x, y;
    
    AIAction ai;
    
    public Enemy(float x, float y, Image image) {
        this.x = x;
        this.y = y;
        
        this.image = image;
        
        ai = new DoNothing();
    }
    
    @Override
    public void draw() {
        image.draw(x-image.getWidth()/2, y-image.getHeight()/2);
    }

    @Override
    public void update(GameState gs) {
        if(ai.execute(this, gs.getDelta())) {
            ai = new DoNothing();
        }
    }
    
    public float getX() { return x; }
    public float getY() { return y; }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }
    
    public void setAI(AIAction ai) {
        this.ai = ai;
    }
    
}
