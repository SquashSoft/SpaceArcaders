/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.awhipple.spacearcaders.ui;

import net.awhipple.spacearcaders.gameobjects.Actor;
import net.awhipple.spacearcaders.views.GameField;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Aaron
 */
public class UIBar implements Actor{

    private int x, y;
    
    private double value;
    private double oldValue;
    
    private Image image;
    private Graphics imageGraphics;
    
    public UIBar(double value, int x, int y) throws SlickException{
        this.value = value;
        oldValue = 100d;
        
        setLocation(x, y);
        
        image = new Image(100,10);
        imageGraphics = image.getGraphics();
        
        setImage();
    }
    
    @Override
    public void init(GameField gf) throws SlickException {}
    
    @Override
    public void draw() {
        image.draw(x, y);
    }

    @Override
    public void update(GameField gf) {
        if(value != oldValue) {
            setImage();
            oldValue = value;
        }
    }
    
    public final void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public void setValue(double value) {
        this.value = value;
    }
    
    private void setImage() {
        imageGraphics.setColor(Color.yellow);
        imageGraphics.clear();
        imageGraphics.fillRect(0, 0, (int) value, 10);
        imageGraphics.flush();
    }
}
