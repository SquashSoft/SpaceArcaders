/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.awhipple.spacearcaders.ai;

import java.util.LinkedList;
import java.util.List;
import net.awhipple.spacearcaders.gameobjects.Enemy;
import net.awhipple.spacearcaders.views.GameField;

/**
 *
 * @author Aaron
 */
public class AI {
    
    private List<AIList> firstActions, loopActions;
    private List<AIList> firstActionsRemovalQueue;
    
    public AI() {
        firstActions = new LinkedList<>();
        
        loopActions = new LinkedList<>();
        
        firstActionsRemovalQueue = new LinkedList<>();
    }
    
    public void execute(Enemy enemy, GameField gf) {
        if(firstActions != null) {
            for(AIList aiList : firstActions) {
                if(!aiList.execute(enemy, gf)) {
                    firstActionsRemovalQueue.add(aiList);
                }
            }
            while(firstActionsRemovalQueue.size() > 0) {
                firstActions.remove(firstActionsRemovalQueue.remove(0));
            }
            if(firstActions.isEmpty()) firstActions = null;
        } else if(loopActions != null) {
            for(AIList aiList : loopActions) {
                aiList.execute(enemy, gf);
            }
        }
    }
    
    public void addAIAction(AIAction aiAction) {
        addActionToList(aiAction, firstActions, 0, false);
    }

    public void addAIAction(AIAction aiAction, int aiList) {
        addActionToList(aiAction, firstActions, aiList, false);        
    }
    
    public void addAILoopAction(AIAction aiAction) {
        addActionToList(aiAction, loopActions, 0, true);
    }

    public void addAILoopAction(AIAction aiAction, int aiList) {
        addActionToList(aiAction, loopActions, aiList, true);
    }
    
    private void addActionToList(AIAction aiAction, List<AIList> list, int listNumber, boolean loop) {
        if(list.size() > listNumber) {
            list.get(listNumber).addAction(aiAction);
        } else if(list.size() == listNumber) {
            list.add(new AIList().setLoop(loop));
            list.get(listNumber).addAction(aiAction);
        } else {
            System.out.println("WARNING: An AI Action was not added to the list because it was beyond the bounds of the array");
        }
    }
}
