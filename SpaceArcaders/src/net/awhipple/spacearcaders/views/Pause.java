/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.awhipple.spacearcaders.views;

import java.util.Collections;
import java.util.List;
import net.awhipple.spacearcaders.utils.GameGlobals;
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

    GameGlobals globals;
    
    View viewPausedFrom;
    Image mask;
    Image pauseImage;
    boolean pauseIsPressed = true;
    boolean returnOnKeyUp = false;
    
    public Pause(GameGlobals globals, View viewPausedFrom) {
        this.globals = globals;
        
        this.viewPausedFrom = viewPausedFrom;
        this.pauseImage = globals.getImage("pause");
        
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
        mask.draw(0, 0, globals.getScreenWidth(), globals.getScreenHeight(), new Color(0,0,0,175));
        pauseImage.draw(globals.getScreenWidth()/2-pauseImage.getWidth()/2,globals.getScreenHeight()/2-pauseImage.getHeight()/2);
    }
    
}
