/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.awhipple.spacearcaders.gameobjects;

import net.awhipple.spacearcaders.utils.GameState;
import org.newdawn.slick.Image;

/**
 *
 * @author Nate
 */
public class Laser implements Actor {

    private float laserLocationX, laserLocationY;
    private float laserSpeed;
    private Image laserIcon;
    
    public Laser (float locationX, float locationY, Image laserImage){

       laserLocationX = locationX;
       laserLocationY = locationY;
       
       laserSpeed = 800.5f;
       
       laserIcon = laserImage;
    }
    @Override
    public void draw() {
        laserIcon.draw( (int) (laserLocationX-laserIcon.getWidth()/2),
            (int) (laserLocationY-laserIcon.getHeight()/2));
    }
    
    @Override
    public void update(GameState gs) {
        float delta = gs.getDelta();
        
        laserLocationY -= (laserSpeed * delta);
    }
    
}
