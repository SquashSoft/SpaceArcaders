/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.awhipple.spacearcaders.ai.actions;

import net.awhipple.spacearcaders.ai.AIAction;
import net.awhipple.spacearcaders.gameobjects.Enemy;
import net.awhipple.spacearcaders.utils.GameState;

/**
 *
 * @author Aaron
 */
public class AIWait implements AIAction{

    private float timeLeft, timerLength;
    
    public AIWait(float timeLeft) {
        this.timeLeft = timeLeft;
        this.timerLength = timeLeft;
    }
    
    @Override
    public CompletionStatus execute(Enemy enemy, GameState gs) {
        float delta = gs.getDelta();
        timeLeft-=delta;
        if(timeLeft <= 0) return CompletionStatus.COMPLETE;
        else return CompletionStatus.NOT_COMPLETE;
    }

    @Override
    public void init() {
        timeLeft = timerLength;
    }
    
}
