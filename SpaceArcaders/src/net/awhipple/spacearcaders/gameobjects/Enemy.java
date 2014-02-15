/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.awhipple.spacearcaders.gameobjects;

import net.awhipple.spacearcaders.ai.*;
import net.awhipple.spacearcaders.ai.actions.*;
import net.awhipple.spacearcaders.graphics.Particle;
import net.awhipple.spacearcaders.graphics.Spark;
import net.awhipple.spacearcaders.utils.GameState;
import net.awhipple.spacearcaders.utils.HitBox;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;

/**
 *
 * @author Aaron
 */
public class Enemy implements Actor {

    private Image image;
    private double x, y;
    private HitBox enemiesHitBox;
    private double enemyHealth;
    private boolean deadEnemy;
    private AI ai;
    
    private double flashTime;
    private boolean flash;
    
    public Enemy(double x, double y, Image image) {
        this.x = x;
        this.y = y;
        
        this.image = image;
        
        this.enemiesHitBox = new HitBox((image.getWidth()/2-5));
        
        enemyHealth = 100d;
        deadEnemy = false;
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
        
        flashTime = 0;
        flash = false;
    }
    
    @Override
    public void draw() {
        if(flash) image.drawFlash((int)(x-image.getWidth()/2), (int)(y-image.getHeight()/2), image.getWidth(), image.getHeight(), new Color(155, 155, 155));
        else image.draw((int)(x-image.getWidth()/2), (int)(y-image.getHeight()/2));
    }

    @Override
    public void update(GameState gs) {
        ai.execute(this, gs);
        
        if(flash) {
            flashTime -= gs.getDelta();
            if(flashTime <= 0) flash = false;
        }
        if(deadEnemy)
        {
            //Particle.createExplosion(gs, x, y, 100, 350);
            Spark.createPixelShower(gs, x, y, 100);
            gs.queueRemoveActor(this);
        }
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

    public void flash() {
        flashTime = .02;
        flash = true;
    }

    public void hitDmg(int i) {
        enemyHealth = enemyHealth - i;
        flash();
        if (enemyHealth <= 0)
            deadEnemy = true;
    }
}
