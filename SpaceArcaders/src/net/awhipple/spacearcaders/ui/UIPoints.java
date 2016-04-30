/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.awhipple.spacearcaders.ui;
import java.util.List;
import net.awhipple.spacearcaders.gameobjects.Actor;
import net.awhipple.spacearcaders.gameobjects.PlayerShip;
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
public class UIPoints extends UIText{
    private int x, y;
    private double value;
    private double oldValue;
    
    private Image image;
    private Graphics imageGraphic;
    
    private UIPoints(int x, int y, List<PlayerShip> myPlayerList) throws SlickException{
        super(x, y, "");
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
