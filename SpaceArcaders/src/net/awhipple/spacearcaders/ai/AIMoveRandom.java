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
public class AIMoveRandom implements AIAction{

    boolean firstRun;
    float toX, toY, xVec, yVec, speed; 
    double rad;
    int xs, ys;
    int SCREEN_W, SCREEN_H;
    
    public AIMoveRandom(int SCREEN_W, int SCREEN_H, float speed) {
        firstRun = true;
        
        this.SCREEN_W = SCREEN_W;
        this.SCREEN_H = SCREEN_H;
        
        this.speed = speed;
    }
    
    @Override
    public CompletionStatus execute(Enemy enemy, float delta) {
        if(firstRun) {
            toX = (int)(Math.random()*SCREEN_W);
            toY = (int)(Math.random()*SCREEN_H);
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
            return CompletionStatus.COMPLETE;
        }
        
        return CompletionStatus.NOT_COMPLETE;
    }

    @Override
    public void init() {
        firstRun = true;
    }
}
