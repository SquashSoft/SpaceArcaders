/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.awhipple.spacearcaders.utils;

/**
 *
 * @author Nate
 */
public class HitBox {
   private int radius;
   
    public HitBox(int radius){
        this.radius = radius;
        
    }
    public boolean collisionCheck(float myX, float myY, float tarX, float tarY, HitBox tarHitBox){
        return collisionCheck((int)myX,(int) myY,(int) tarX,(int) tarY, tarHitBox);
    }
    public boolean collisionCheck(int myX, int myY, int tarX, int tarY, HitBox tarHitBox){
        int xdif = tarX - myX;
        int ydif = tarY - myY;
        int totalRad = (tarHitBox.getRad()+radius);
        return ((xdif*xdif) + (ydif*ydif)) <= (totalRad*totalRad);
    }

    private int getRad() {return this.radius;}
}
