/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.awhipple.spacearcaders.views;

import org.newdawn.slick.Input;

/**
 *
 * @author Aaron
 */
public interface View {
    public void update(Input input);
    public void render();
}
