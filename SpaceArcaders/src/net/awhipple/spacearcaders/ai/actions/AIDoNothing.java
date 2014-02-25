/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.awhipple.spacearcaders.ai.actions;

import net.awhipple.spacearcaders.ai.AIAction;
import net.awhipple.spacearcaders.gameobjects.Enemy;
import net.awhipple.spacearcaders.views.GameField;

/**
 *
 * @author Aaron
 */
public class AIDoNothing implements AIAction{

    @Override
    public CompletionStatus execute(Enemy enemy, GameField gf) {
        return CompletionStatus.NOT_COMPLETE;
    }

    @Override
    public void init() {
        
    }
    
}
