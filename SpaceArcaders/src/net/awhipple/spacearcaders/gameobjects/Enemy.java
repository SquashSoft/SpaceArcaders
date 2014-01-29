/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.awhipple.spacearcaders.gameobjects;

import net.awhipple.spacearcaders.ai.AI;
import net.awhipple.spacearcaders.ai.AIAction;
import net.awhipple.spacearcaders.ai.AIAction.CompletionStatus;
import net.awhipple.spacearcaders.ai.AIWait;
import net.awhipple.spacearcaders.ai.MoveTo;
import net.awhipple.spacearcaders.utils.GameState;
import org.newdawn.slick.Image;

/**
 *
 * @author Aaron
 */
public class Enemy implements Actor {

    private Image image;
    private float x, y;
        
    private AI ai;
    private boolean curDir;
    
    public Enemy(float x, float y, Image image) {
        this.x = x;
        this.y = y;
        
        this.image = image;
        
        ai = new AI(new AIWait(1f));
        curDir = false;
    }
    
    @Override
    public void draw() {
        image.draw(x-image.getWidth()/2, y-image.getHeight()/2);
    }

    @Override
    public void update(GameState gs) {
        if(ai.execute(this, gs.getDelta()) == CompletionStatus.COMPLETE) {
            ai.setAIAction( curDir ? new MoveTo(gs.getScreenWidth()-100, gs.getScreenHeight()/4, 300)
                                   : new MoveTo(100, gs.getScreenHeight()/4, 300));
            curDir = !curDir;
        }
    }
    
    public float getX() { return x; }
    public float getY() { return y; }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }
    
    public void setAI(AIAction aiAction) {
        this.ai.setAIAction(aiAction);
    }
}
