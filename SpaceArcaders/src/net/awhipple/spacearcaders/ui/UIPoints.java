/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.awhipple.spacearcaders.ui;
import net.awhipple.spacearcaders.gameobjects.Actor;
import net.awhipple.spacearcaders.utils.GameGlobals;
import net.awhipple.spacearcaders.views.GameField;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Nathan
 */
public class UIPoints {
    private int x, y;
    private double value;
    private double oldValue;
    
    private Image image;
    private Graphics imageGraphic;
    
    private UIPoints(int value, int x, int y) throws SlickException{
        this.value = value;
        oldValue = 0;
        
        setLocation(x,y);
        
    }
    
    public void draw(){
        image.draw(x,y);
    }
    
    public void setValue(double value){
        this.value = value;
    }
    
    private void setImage(){
        
    }

    private void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
}
