/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.awhipple.spacearcaders.utils;

import net.awhipple.spacearcaders.gameobjects.Actor;
import net.awhipple.spacearcaders.gameobjects.StarMap;
import net.awhipple.spacearcaders.gameobjects.PlayerShip;
import net.awhipple.spacearcaders.gameobjects.Laser;
import java.util.LinkedList;
import java.util.List;
import net.awhipple.spacearcaders.ui.UIImage;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Aaron
 */
public class GameState {
    private Input input;
    private int SCREEN_W, SCREEN_H;
    private float delta;
    
    private List<Actor> actorList;
    private List<Actor> actorsToBeAdded;
    private List<Actor> actorsToBeRemoved;
    
    private ImageLibrary imageLibrary;
    private SoundLibrary soundLibrary;
    
    private UIImage pauseObject;
    
    public GameState(int SCREEN_W, int SCREEN_H, int TARGET_FPS) throws Exception {
        this.SCREEN_W = SCREEN_W;
        this.SCREEN_H = SCREEN_H;
        
        delta = 1f / (float)TARGET_FPS;
        
        actorList = new LinkedList<>();
        actorsToBeAdded = new LinkedList<>();
        actorsToBeRemoved = new LinkedList<>();
        
        imageLibrary = new ImageLibrary();
        soundLibrary = new SoundLibrary();
        
        pauseObject = null;
        
        loadResources();
        
        createStarMap();
        
        processActorQueues();
    }

    public void updateActors() {
        for(Actor curActor : actorList) {
            curActor.update(this);
        }
        processActorQueues();
    }
    
    public void renderActors() {
        for (Actor curActor : actorList) {
            curActor.draw();
        }
    }
    
    public void queueNewActor(Actor actor) {
        actorsToBeAdded.add(actor);
    }
    
    public void queueRemoveActor(Actor actor) {
        actorsToBeRemoved.add(actor);
    }
    
    public final void processActorQueues() {
        while(actorsToBeAdded.size() > 0)
            actorList.add(actorsToBeAdded.remove(0));
        while(actorsToBeRemoved.size() > 0)
            actorList.remove(actorsToBeRemoved.remove(0));
    }
   
    public void pause(Image pauseImage) {
        if(pauseObject == null) {
            pauseObject = new UIImage(SCREEN_W/2, SCREEN_H/2, pauseImage);
            queueNewActor(pauseObject);
            processActorQueues();
        }
    }
   
    public void unpause() { 
        if(pauseObject != null) {
            queueRemoveActor(pauseObject);
            processActorQueues();
            pauseObject = null;
        }
    }

    public void playSound(String key) { soundLibrary.getSound(key).play(1f, .02f); }
    
    public void setInput(Input input) { this.input = input; }
    public Input getInput() { return input; }

    public Image getImage(String key) { return imageLibrary.getImage(key); }
    
    public float getDelta() { return delta; }

    private void createStarMap() throws SlickException {
        queueNewActor(new StarMap(SCREEN_W, SCREEN_H));
    }
    
    private void loadResources() throws Exception {
        imageLibrary.loadImage("laser", "data/images/proto-laser.PNG");
        imageLibrary.loadImage("ship",  "data/images/proto-ship.PNG");
        imageLibrary.loadImage("pause", "data/images/pause.PNG");
        
        soundLibrary.loadSound("laser", "data/sounds/laser.wav");
    }
}
