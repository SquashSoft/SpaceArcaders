/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.awhipple.spacearcaders.ui;

import net.awhipple.spacearcaders.gameobjects.Actor;
import net.awhipple.spacearcaders.utils.GameGlobals;
import net.awhipple.spacearcaders.views.GameField;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Aaron
 */
public class UIImage implements Actor {

    private Image image;
    private int xLocation, yLocation;
    
    public UIImage(int x, int y, Image uiImage) {
        xLocation = x;
        yLocation = y;
        image = uiImage;
    }
    
    @Override
    public void init(GameField gf) throws SlickException {}
    
    @Override
    public void draw() {
        image.draw((int) (xLocation - image.getWidth()/2), (int) (yLocation - image.getHeight()/2));
    }

    @Override
    public void update(GameField gf) {
        
    }
    
}
