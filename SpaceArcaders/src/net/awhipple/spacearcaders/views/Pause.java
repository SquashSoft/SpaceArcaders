/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.awhipple.spacearcaders.views;

import java.util.Collections;
import java.util.List;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Aaron
 */
public class Pause implements View{

    View viewPausedFrom;
    int screenWidth, screenHeight;
    Image mask;
    Image pauseImage;
    boolean pauseIsPressed = true;
    boolean returnOnKeyUp = false;
    
    public Pause(View viewPausedFrom, Image pauseImage, int screenWidth, int screenHeight) {
        this.viewPausedFrom = viewPausedFrom;
        this.pauseImage = pauseImage;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        
        try {
            mask = new Image(1, 1);
            Graphics grphx = mask.getGraphics();
            grphx.setColor(Color.black);
            grphx.fillRect(0, 0, 1, 1);
            grphx.flush();
        } catch(SlickException ex) {
            System.out.println("Unable to make mask in Pause view");
        }
    }
    
    @Override
    public List<ViewInstruction> update(Input input) {
        if(!input.isKeyDown(Input.KEY_P)) pauseIsPressed = false;
        if(input.isKeyDown(Input.KEY_P) && !pauseIsPressed) returnOnKeyUp = true;
        if(!input.isKeyDown(Input.KEY_P) && returnOnKeyUp) {
            return Collections.singletonList(new ViewInstruction(ViewInstruction.Set.SWITCH_VIEW, viewPausedFrom));
        }
        return null;
    }

    @Override
    public void render() {
        viewPausedFrom.render();
        mask.draw(0, 0, screenWidth, screenHeight, new Color(0,0,0,175));
        pauseImage.draw(screenWidth/2-pauseImage.getWidth()/2,screenHeight/2-pauseImage.getHeight()/2);
    }
    
}
