/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.awhipple.spacearcaders.ai;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import net.awhipple.spacearcaders.gameobjects.Enemy;
import net.awhipple.spacearcaders.views.GameField;

/**
 *
 * @author Aaron
 */
public class AIList {
    private List<AIAction> aiActionList;
    private Iterator<AIAction> aiActionListIterator;
    private AIAction currentAction;
    private boolean loop, loopSafety;
    
    public AIList() {
        aiActionList = new LinkedList<>();
        aiActionListIterator = null;
        currentAction = null;
        loop = false;
        loopSafety = false;
    }

    public void addAction(AIAction aiAction) {
        aiActionList.add(aiAction);
    }

    /**
     * @param enemy Enemy to run the AI on
     * @param delta the time step to use
     * @return Returns true only when all actions have been completed;
     */
    public boolean execute(Enemy enemy, GameField gf) {
        if(currentAction == null && !getNextAction()) return false;
        AIAction.CompletionStatus status = AIAction.CompletionStatus.COMPLETE_REEXECUTE;
        while(status == AIAction.CompletionStatus.COMPLETE_REEXECUTE) {
            status = currentAction.execute(enemy, gf);
            if(status != AIAction.CompletionStatus.COMPLETE_REEXECUTE) loopSafety = true;
            if(status == AIAction.CompletionStatus.COMPLETE_REEXECUTE ||
               status == AIAction.CompletionStatus.COMPLETE) {
                if(!getNextAction()) return false;
            }
        }
        return true;
    }
    
    private boolean getNextAction() {
        if(aiActionListIterator == null) aiActionListIterator = aiActionList.iterator();
        if(aiActionListIterator.hasNext()) {
            currentAction = aiActionListIterator.next();
            return true;
        } else if(loop) {
            for(AIAction aiAction : aiActionList) {
                aiAction.init();
            }
            aiActionListIterator = aiActionList.iterator();
            if(aiActionListIterator.hasNext()) {
                currentAction = aiActionListIterator.next();
                if(!loopSafety) return false;
                loopSafety = false;
                return true;
            } else { return false; }
        } else {
            return false;
        }
    }

    public AIList setLoop(boolean loop) {
        this.loop = loop;
        return this;
    }
}

