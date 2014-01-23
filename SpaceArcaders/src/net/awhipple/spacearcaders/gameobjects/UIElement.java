/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.awhipple.spacearcaders.gameobjects;

import net.awhipple.spacearcaders.utils.GameState;
import org.newdawn.slick.Image;

/**
 *
 * @author Aaron
 */
public class UIElement implements Actor {

    Image image;
    int xLocation, yLocation;
    
    public UIElement(int x, int y, Image uiImage) {
        xLocation = x;
        yLocation = y;
        image = uiImage;
    }
    
    @Override
    public void draw() {
        image.draw((int) (xLocation - image.getWidth()/2), (int) (yLocation - image.getHeight()/2));
    }

    @Override
    public void update(GameState gs) {
        
    }
    
}
