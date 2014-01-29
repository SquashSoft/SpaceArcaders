/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.awhipple.spacearcaders.ai;

import net.awhipple.spacearcaders.gameobjects.Enemy;

/**
 *
 * @author Aaron
 */
public class AIWait implements AIAction{

    private float timeLeft;
    
    public AIWait(float timeLeft) {
        this.timeLeft = timeLeft;
    }
    
    @Override
    public CompletionStatus execute(Enemy enemy, float delta) {
        timeLeft-=delta;
        if(timeLeft <= 0) return CompletionStatus.COMPLETE;
        else return CompletionStatus.NOT_COMPLETE;
    }
    
}
