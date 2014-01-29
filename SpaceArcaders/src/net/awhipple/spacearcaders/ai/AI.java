/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.awhipple.spacearcaders.ai;

import java.util.Iterator;
import java.util.List;
import net.awhipple.spacearcaders.gameobjects.Enemy;
import net.awhipple.spacearcaders.utils.Pair;

/**
 *
 * @author Aaron
 */
public class AI {
    
    private AIAction aiAction;
    private List<Pair<Iterator<AIAction>, List<AIAction>>> firstActions, loopActions;
    
    public AI(AIAction aiAction) {
        this.aiAction = aiAction;
    }
    
    public AIAction.CompletionStatus execute(Enemy enemy, float delta) {
        return aiAction.execute(enemy, delta);
    }
    
    public void setAIAction(AIAction aiAction) {
        this.aiAction = aiAction;
    }
}
