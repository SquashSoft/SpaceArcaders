/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.awhipple.spacearcaders.ai.actions;

import net.awhipple.spacearcaders.gameobjects.Enemy;
import net.awhipple.spacearcaders.utils.GameState;

/**
 *
 * @author Aaron
 */
public class AIMoveDistance extends AIMoveTo{

    double xDis, yDis;
    
    public AIMoveDistance(double x, double y, double speed) {
        super(x, y, speed);
        xDis = x;
        yDis = y;
    }
    
    @Override
    public CompletionStatus execute(Enemy enemy, GameState gs) {
        if(firstRun) {
            toX = enemy.getX() + xDis;
            toY = enemy.getY() + yDis;
        }
        
        return super.execute(enemy, gs);
    }
}
