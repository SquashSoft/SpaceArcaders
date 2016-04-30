/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
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
 * @author Nathan
 */
public class UIText implements Actor {
    public int x,y;
    public String myCurrentText, oldText;
    
    private Image image;
    private Graphics myGraphic;
    
    
    public UIText(int x, int y, String text) throws SlickException{
       this.x = x;
       this.y = y;
       this.myCurrentText = text;
       this.oldText = "";
       
       image = new Image(1000, 30);
       myGraphic = image.getGraphics();
               
       setImage();
    }
    
    @Override
    public void init(GameField gf) throws SlickException {}

    @Override
    public void draw() {
        image.draw(x,y);
    }

    @Override
    public void update(GameField gf) {
        if(!myCurrentText.equals(oldText)){
            setImage();
            oldText = myCurrentText;
        }
    }

    private void setImage() {
        myGraphic.setColor(Color.orange);
        myGraphic.clear();
        myGraphic.drawString(myCurrentText, 0,0);
        myGraphic.flush();
    }
  
    
    
}
