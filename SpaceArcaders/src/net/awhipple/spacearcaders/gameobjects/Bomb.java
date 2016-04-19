/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.awhipple.spacearcaders.gameobjects;

import java.util.List;
import net.awhipple.spacearcaders.graphics.Particle;
import net.awhipple.spacearcaders.graphics.Spark;
import net.awhipple.spacearcaders.views.GameField;
import net.awhipple.spacearcaders.utils.HitBox;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Nate
 */
public class Bomb implements Actor {

    private double bombLocationX, bombLocationY;
    private double bombSpeed;
    private double birthPlaceY;
    private Image bombIcon;
    private HitBox bombsHitBox;
    private List<Target> targetList;
    private String targetType;
    
    public Bomb (double locationX, double locationY, double bombSpeed, String targetType, Image bombImage){

       bombLocationX = locationX;
       bombLocationY = locationY;
       birthPlaceY = locationY;
       
       this.bombSpeed = bombSpeed;
       bombIcon = bombImage;
       bombsHitBox = new HitBox((bombIcon.getWidth()/2-5));
       targetList = null;
       this.targetType = targetType;
    }
    
    @Override
    public void init(GameField gf) throws SlickException {
        targetList = gf.getTargetList(targetType);
    }
    
    @Override
    public void draw() {
        bombIcon.draw( (int) (bombLocationX-bombIcon.getWidth()/2),
            (int) (bombLocationY-bombIcon.getHeight()/2));
    }
    
    @Override
    public void update(GameField gf) {
        double delta = gf.getDelta();
        bombLocationY -= (bombSpeed * delta);
        if(        bombLocationY<-100
                || bombLocationY>gf.getGlobals().getScreenHeight()+100
                || bombLocationX>gf.getGlobals().getScreenWidth()+100
                || bombLocationX<-100) {
                gf.queueRemoveActor(this);
        }
        if( bombLocationY <= (birthPlaceY -400)){
                detonate(gf);
                }
        boolean bombCollided = false;
        for(Target target : targetList){
            if(bombsHitBox.collisionCheck(bombLocationX, bombLocationY, target.getX(), target.getY(), target.getHitBox())){
                Particle.createExplosion(gf, bombLocationX, bombLocationY, 30, 100);
                Spark.createPixelShower(gf, bombLocationX, bombLocationY, 7);
                //target.dealDamage(25);
                gf.getGlobals().playSound("explode");
                detonate(gf);
                bombCollided = true;
                break;
            }
        }
        if(bombCollided)
            gf.queueRemoveActor(this);
    }
    public void detonate(GameField gf){
        Particle.createExplosion(gf, bombLocationX, bombLocationY, 30, 100);  //create explosion & pixel shower
        Spark.createPixelShower(gf, bombLocationX, bombLocationY, 10);        //run bombsuccess function
        gf.clearEnemies();                                                     //remove bomb from live actors
        gf.queueRemoveActor(this);
    }
    
}