/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.awhipple.spacearcaders.gameobjects;

import net.awhipple.spacearcaders.graphics.Particle;
import net.awhipple.spacearcaders.graphics.Spark;
import net.awhipple.spacearcaders.utils.GameState;
import net.awhipple.spacearcaders.utils.HitBox;
import org.newdawn.slick.Image;

/**
 *
 * @author Nate
 */
public class Laser implements Actor {

    private double laserLocationX, laserLocationY;
    private double laserSpeed;
    private Image laserIcon;
    private HitBox lasersHitBox;
    
    public Laser (double locationX, double locationY, Image laserImage){

       laserLocationX = locationX;
       laserLocationY = locationY;
       
       laserSpeed = 800d;
       laserIcon = laserImage;
       lasersHitBox = new HitBox((laserIcon.getWidth()/2-5));
    }
    @Override
    public void draw() {
        laserIcon.draw( (int) (laserLocationX-laserIcon.getWidth()/2),
            (int) (laserLocationY-laserIcon.getHeight()/2));
    }
    
    @Override
    public void update(GameState gs) {
        double delta = gs.getDelta();
        laserLocationY -= (laserSpeed * delta);
        if(        laserLocationY<-100
                || laserLocationY>gs.getScreenHeight()+100
                || laserLocationX>gs.getScreenWidth()+100
                || laserLocationX<-100) 
                gs.queueRemoveActor(this);
        boolean laserCollided = false;
        for(Enemy en : gs.getEnemyList()){
            if(lasersHitBox.collisionCheck(laserLocationX, laserLocationY, en.getX(), en.getY(), en.getHitBox())){
                Particle.createExplosion(gs, laserLocationX, laserLocationY, 30, 100);
                Spark.createPixelShower(gs, laserLocationX, laserLocationY, 7);
                en.hitDmg(25);
                gs.playSound("explode");
                laserCollided = true;
                break;
            }
        }
        if(laserCollided)
            gs.queueRemoveActor(this);
    }
}