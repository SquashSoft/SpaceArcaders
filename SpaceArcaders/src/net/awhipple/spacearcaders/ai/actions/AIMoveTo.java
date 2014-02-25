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
public class AIMoveTo implements AIAction{

    protected boolean firstRun;
    protected double toX, toY, xVec, yVec, speed; 
    protected double rad;
    protected int xs, ys;
    
    public AIMoveTo(double x, double y, double speed) {
        firstRun = true;
        
        toX = x;
        toY = y;
        
        this.speed = speed;
    }
    
    @Override
    public CompletionStatus execute(Enemy enemy, GameField gf) {
        double delta = gf.getDelta();
        if(firstRun) {
            rad = GameMath.pointsToRad(enemy.getX(), enemy.getY(), toX, toY);
            xVec = (double) Math.cos(rad);
            yVec = (double) Math.sin(rad);
            xs = GameMath.whichSide(enemy.getX(), toX);
            ys = GameMath.whichSide(enemy.getY(), toY);
            firstRun = false;
        }
        
        enemy.setPosition(enemy.getX()+xVec*speed*delta, enemy.getY()+yVec*speed*delta);
        
        if( (xs != 0 && GameMath.whichSide(enemy.getX(), toX) != xs) ||
            (ys != 0 && GameMath.whichSide(enemy.getY(), toY) != ys)) {
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
