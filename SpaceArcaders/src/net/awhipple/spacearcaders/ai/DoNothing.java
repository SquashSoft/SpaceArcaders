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
public class DoNothing implements AIAction{

    @Override
    public CompletionStatus execute(Enemy enemy, float delta) {
        return CompletionStatus.NOT_COMPLETE;
    }

    @Override
    public void init() {
        
    }
    
}
