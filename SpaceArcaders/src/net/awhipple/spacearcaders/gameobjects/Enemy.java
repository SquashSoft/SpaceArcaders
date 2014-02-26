/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.awhipple.spacearcaders.gameobjects;

import net.awhipple.spacearcaders.ai.*;
import net.awhipple.spacearcaders.ai.actions.*;
import net.awhipple.spacearcaders.graphics.Spark;
import net.awhipple.spacearcaders.utils.GameGlobals;
import net.awhipple.spacearcaders.views.GameField;
import net.awhipple.spacearcaders.utils.HitBox;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Aaron
 */
public class Enemy implements Actor, Target {

    private Image image;
    private double x, y;
    private double size;
    private HitBox enemiesHitBox;
    private double enemyHealth;
    private AI ai;
    
    private double flashTime;
    private boolean flash;
    private boolean spawnChildren;
    
    public Enemy(double x, double y, double size, Image image) {
        this(x, y, image);
        this.size = size;
        this.enemiesHitBox = new HitBox((int)((image.getWidth()/2-5)*size));
        this.enemyHealth = enemyHealth * size*size;
    }
    
    public Enemy(double x, double y, Image image) {
        this.x = x;
        this.y = y;
        this.size = 1;
        
        this.image = image;
        
        this.enemiesHitBox = new HitBox((image.getWidth()/2-5));
        
        enemyHealth = 75d;
        ai = new AI();
        
        ai.addAIAction(new AIMoveRandom(300), 0);
        
        ai.addAILoopAction(new AIMoveRandom(300), 0);
        
        ai.addAILoopAction(new AIFire(), 1);
        ai.addAILoopAction(new AIWait(2), 1);
                
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
        spawnChildren = true;        
    }
    
    @Override
    public void init(GameField gf) throws SlickException {}
    
    @Override
    public void draw() {
        int width = (int)(image.getWidth()*size);
        int height = (int)(image.getHeight()*size);
        if(flash) image.drawFlash((int)(x-width/2), (int)(y-height/2), width, height, new Color(155, 155, 155));
        else image.draw((int)(x-width/2), (int)(y-height/2), width, height);
    }

    @Override
    public void update(GameField gf) {
        ai.execute(this, gf);
        
        if(flash) {
            flashTime -= gf.getDelta();
            if(flashTime <= 0) flash = false;
        }
        for(PlayerShip player : gf.getPlayerShipList()) {
            if(enemiesHitBox.collisionCheck(x, y, player.getX(), player.getY(), player.getHitBox())) {
                player.dealDamage(25);
                gf.getGlobals().playSound("explode");
                enemyHealth = 0;
                spawnChildren = false;
            }
        }
        if(enemyHealth <= 0) {
            Spark.createPixelShower(gf, x, y, (int)(100f*size*size*size), 700*size);
            gf.queueRemoveActor(this);
            if(spawnChildren && getSize() > .6) {
                for(int i = 0; i < 2; i++) {
                    Image impImage = image == gf.getGlobals().getImage("imp-red", true) ? gf.getGlobals().getImage("imp-green", true) :
                                     image == gf.getGlobals().getImage("imp-green", true) ? gf.getGlobals().getImage("imp-blue", true) :
                                     gf.getGlobals().getImage("imp-red", true);
                    gf.queueNewActor(new Enemy((int)(x), y, getSize()*.75, impImage));
                }
            }
        }
    }
    
    public void fire(GameField gf) {
        gf.queueNewActor(new Laser(x, y, -600,  "player", gf.getGlobals().getImage("laser", true)));
    }
    
    @Override
    public double getX() { return x; }
    @Override
    public double getY() { return y; }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    @Override
    public HitBox getHitBox(){ return enemiesHitBox; }
    
    public void setAI(AI ai) { this.ai = ai; }
    
    public double getSize() { return size; }
    
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

    @Override
    public void dealDamage(double i) {
        enemyHealth = enemyHealth - i;
        flash();
    }
 
}
