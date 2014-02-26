/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.awhipple.spacearcaders.ai.actions;

import net.awhipple.spacearcaders.ai.AIAction;
import net.awhipple.spacearcaders.gameobjects.Enemy;
import net.awhipple.spacearcaders.utils.GameMath;
import net.awhipple.spacearcaders.views.GameField;

/**
 *
 * @author Aaron
 */
public class AIMoveRandom implements AIAction{

    boolean firstRun;
    double toX, toY, xVec, yVec, speed; 
    double rad;
    int xs, ys;
    
    public AIMoveRandom(double speed) {
        firstRun = true;
        
        this.speed = speed;
    }
    
    @Override
    public CompletionStatus execute(Enemy enemy, GameField gf) {
        double delta = gf.getDelta();
        if(firstRun) {
            toX = (int)(Math.random()*gf.getGlobals().getScreenWidth());
            toY = (int)(Math.random()*gf.getGlobals().getScreenHeight());
            rad = GameMath.pointsToRad(enemy.getX(), enemy.getY(), toX, toY);
            xVec = Math.cos(rad);
            yVec = Math.sin(rad);
            xs = GameMath.whichSide(enemy.getX(), toX);
            ys = GameMath.whichSide(enemy.getY(), toY);
            firstRun = false;
        }
        
        enemy.setPosition(enemy.getX()+xVec*speed*delta, enemy.getY()+yVec*speed*delta);
        
        if( GameMath.whichSide(enemy.getX(), toX) != xs ||
            GameMath.whichSide(enemy.getY(), toY) != ys) {
            enemy.setPosition(toX, toY);
            return CompletionStatus.COMPLETE;
        }
        
        return CompletionStatus.NOT_COMPLETE;
    }

    @Override
    public void init() {
        firstRun = true;
    }
}
