/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.awhipple.spacearcaders.ai;

import net.awhipple.spacearcaders.gameobjects.Enemy;
import net.awhipple.spacearcaders.utils.GameMath;

/**
 *
 * @author Aaron
 */
public class MoveTo implements AIAction{

    boolean firstRun;
    float toX, toY, xVec, yVec, speed; 
    double rad;
    int xs, ys;
    
    public MoveTo(float x, float y, float speed) {
        firstRun = true;
        
        toX = x;
        toY = y;
        
        this.speed = speed;
    }
    
    @Override
    public boolean execute(Enemy enemy, float delta) {
        if(firstRun) {
            rad = GameMath.pointsToRad(enemy.getX(), enemy.getY(), toX, toY);
            xVec = (float) Math.cos(rad);
            yVec = (float) Math.sin(rad);
            xs = GameMath.whichSide(enemy.getX(), toX);
            ys = GameMath.whichSide(enemy.getY(), toY);
            firstRun = false;
        }
        
        enemy.setPosition(enemy.getX()+xVec*speed*delta, enemy.getY()+yVec*speed*delta);
        
        if( GameMath.whichSide(enemy.getX(), toX) != xs ||
            GameMath.whichSide(enemy.getY(), toY) != ys) {
            return true;
        }
        
        return false;
    }
    
    
}
