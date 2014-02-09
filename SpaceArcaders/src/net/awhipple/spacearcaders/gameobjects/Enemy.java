/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.awhipple.spacearcaders.gameobjects;

import net.awhipple.spacearcaders.ai.*;
import net.awhipple.spacearcaders.ai.actions.*;
import net.awhipple.spacearcaders.utils.GameState;
import net.awhipple.spacearcaders.utils.HitBox;
import org.newdawn.slick.Image;

/**
 *
 * @author Aaron
 */
public class Enemy implements Actor {

    private Image image;
    private double x, y;
    private HitBox enemiesHitBox;
        
    private AI ai;
    
    public Enemy(double x, double y, Image image) {
        this.x = x;
        this.y = y;
        
        this.image = image;
        
        this.enemiesHitBox = new HitBox((image.getWidth()/2-5));
        
        ai = new AI();
        
        ai.addAIAction(new AIMoveRandom(300), 0);
        
        ai.addAILoopAction(new AIMoveRandom(300), 0);
                
        //ai.addAILoopAction(new AIWait(.5), 1);
        //ai.addAILoopAction(new AIFire(), 1);

        /*int speed = 300;
        
        ai.addAIAction(new AIMoveDistance(0,400,speed));
        
        ai.addAILoopAction(new AIMoveDistance(0,    300, speed));
        ai.addAILoopAction(new AIMoveDistance(300,    0, speed));
        ai.addAILoopAction(new AIMoveDistance(0,   -300, speed));
        ai.addAILoopAction(new AIMoveDistance(-300,   0, speed));
        */
    }
    
    @Override
    public void draw() {
        image.draw((int)(x-image.getWidth()/2), (int)(y-image.getHeight()/2));
    }

    @Override
    public void update(GameState gs) {
        ai.execute(this, gs);
    }
    
    public void fire(GameState gs) {
        gs.queueNewActor(new Laser(x, y, gs.getImage("laser")));
    }
    
    public double getX() { return x; }
    public double getY() { return y; }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public HitBox getHitBox(){ return enemiesHitBox; }
    
    public void setAI(AI ai) { this.ai = ai; }
    
    //Work in progress
    public static Enemy createEnemy() {
        Enemy en = new Enemy(0, 0, null);
        AI ai = new AI();
        try {
            ai.addAILoopAction((AIAction)Class.forName("net.awhipple.spacearcaders.ai.actions.AIMoveRandom").getConstructor(double.class).newInstance(new Object[] {300d}), 0);
        } catch(Exception ex) {
            System.out.println("Warning: Failed to add ai action to enemy - " + ex.getMessage());
        }
        en.setAI(ai);
        return en;
    }
}
