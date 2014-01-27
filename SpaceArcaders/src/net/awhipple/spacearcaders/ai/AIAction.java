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
public interface AIAction {
    public boolean execute(Enemy enemy, float delta);
}
