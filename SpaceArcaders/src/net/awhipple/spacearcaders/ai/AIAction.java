/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.awhipple.spacearcaders.ai;

import net.awhipple.spacearcaders.gameobjects.Enemy;
import net.awhipple.spacearcaders.utils.GameState;

/**
 * Execute should return one of the following enums.
 *  
 * NOT_COMPLETE - If your action is not complete and you expect
 *                 to be called next game loop.
 * 
 * COMPLETE     - You have finished processing and have nothing left to do.
 *                For instance, a movement AI that just moved and is at the
 *                destination.
 * 
 * COMPLETE_REEXECUTE - You have finished processing and have nothing left to
 *                      do. This one is used when the next AI action should be
 *                      called immediately, for instance in a firing AI action.
 * 
 * init should be used for all AIActions. If your AIAction is put in a loop, 
 * your action may be run again with the expectation that it will start from
 * scratch. If this is the case the init function will first be called to give
 * you a chance to re start any variables.
 * 
 * @author Aaron
 */
public interface AIAction {
    
    public static enum CompletionStatus {
        NOT_COMPLETE,
        COMPLETE,
        COMPLETE_REEXECUTE;
    }
    
    public CompletionStatus execute(Enemy enemy, GameState gs);
    public void init();
}
