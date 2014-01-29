/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.awhipple.spacearcaders.ai;

import java.util.LinkedList;
import java.util.List;
import net.awhipple.spacearcaders.gameobjects.Enemy;
import net.awhipple.spacearcaders.utils.GameState;

/**
 *
 * @author Aaron
 */
public class AI {
    
    private List<AIList> firstActions, loopActions;
    private List<AIList> firstActionsRemovalQueue;
    
    public AI() {
        firstActions = new LinkedList<>();
        firstActions.add(new AIList());
        
        loopActions = new LinkedList<>();
        loopActions.add((new AIList().setLoop(true)));
        
        firstActionsRemovalQueue = new LinkedList<>();
    }
    
    public void execute(Enemy enemy, GameState gs) {
        if(firstActions != null) {
            for(AIList aiList : firstActions) {
                if(!aiList.execute(enemy, gs)) {
                    firstActionsRemovalQueue.add(aiList);
                }
            }
            while(firstActionsRemovalQueue.size() > 0) {
                firstActions.remove(firstActionsRemovalQueue.remove(0));
            }
            if(firstActions.isEmpty()) firstActions = null;
        } else if(loopActions != null) {
            for(AIList aiList : loopActions) {
                aiList.execute(enemy, gs);
            }
        }
    }
    
    public void addAIAction(AIAction aiAction) {
        firstActions.get(0).addAction(aiAction);
    }
    
    public void addAILoopAction(AIAction aiAction) {
        loopActions.get(0).addAction(aiAction);
    }
}
