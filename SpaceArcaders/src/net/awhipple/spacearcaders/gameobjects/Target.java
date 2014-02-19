/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.awhipple.spacearcaders.gameobjects;

import net.awhipple.spacearcaders.utils.HitBox;

/**
 *
 * @author Nate
 */
public interface Target {
    public HitBox getHitBox();
    public double getX();
    public double getY();
    public void dealDamage(double damage);
}
