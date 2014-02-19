/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.awhipple.spacearcaders.gameobjects;

import java.util.List;
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
    private List<Target> targetList;
    private String targetType;
    
    public Laser (double locationX, double locationY, double laserSpeed, String targetType, Image laserImage){

       laserLocationX = locationX;
       laserLocationY = locationY;
       
       this.laserSpeed = laserSpeed;
       laserIcon = laserImage;
       lasersHitBox = new HitBox((laserIcon.getWidth()/2-5));
       targetList = null;
       this.targetType = targetType;
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
        if(targetList == null)
            targetList = gs.getTargetList(targetType);
        boolean laserCollided = false;
        for(Target target : targetList){
            if(lasersHitBox.collisionCheck(laserLocationX, laserLocationY, target.getX(), target.getY(), target.getHitBox())){
                Particle.createExplosion(gs, laserLocationX, laserLocationY, 30, 100);
                Spark.createPixelShower(gs, laserLocationX, laserLocationY, 7);
                target.dealDamage(25);
                gs.playSound("explode");
                laserCollided = true;
                break;
            }
        }
        if(laserCollided)
            gs.queueRemoveActor(this);
    }
}