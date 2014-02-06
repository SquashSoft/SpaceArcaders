/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.awhipple.spacearcaders.utils;

import net.awhipple.spacearcaders.gameobjects.Actor;
import net.awhipple.spacearcaders.gameobjects.StarMap;
import java.util.LinkedList;
import java.util.List;
import net.awhipple.spacearcaders.gameobjects.Enemy;
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
    private double delta, targetDelta;
    
    private List<Actor> actorList;
    private List<Actor> actorsToBeAdded;
    private List<Actor> actorsToBeRemoved;
    private List<Enemy> enemyList;
    
    private ImageLibrary imageLibrary;
    private SoundLibrary soundLibrary;
    
    private UIImage pauseObject;
    
    public GameState(int SCREEN_W, int SCREEN_H, int TARGET_FPS) throws Exception {
        this.SCREEN_W = SCREEN_W;
        this.SCREEN_H = SCREEN_H;
        
        targetDelta = 1d / (double)TARGET_FPS;
        delta = targetDelta;
        
        actorList = new LinkedList<>();
        enemyList = new LinkedList<>();
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
        while(actorsToBeAdded.size() > 0) {
            Actor actor = actorsToBeAdded.remove(0);
            actorList.add(actor);
            if(actor instanceof Enemy)
                enemyList.add((Enemy) actor);
        }
        while(actorsToBeRemoved.size() > 0) {
            Actor actor = actorsToBeRemoved.remove(0);
            actorList.remove(actor);
            if(actor instanceof Enemy)
                enemyList.remove((Enemy) actor);
        }
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
    
    public double getDelta() { return delta; }
    
    public int getScreenWidth() { return SCREEN_W; }
    public int getScreenHeight() { return SCREEN_H; }
    
    public void setDeltaFromFps(int fps) {
        if(fps == 0) delta = targetDelta;
        else this.delta = 1d/(double)fps;
    }

    private void createStarMap() throws SlickException {
        queueNewActor(new StarMap(SCREEN_W, SCREEN_H));
    }
    
    public List<Enemy> getEnemyList() {
        return enemyList;
    }
     
    private void loadResources() throws Exception {
        imageLibrary.loadImage("ship",  "data/images/proto-ship.PNG");
        imageLibrary.loadImage("laser", "data/images/proto-laser.PNG");
        
        imageLibrary.loadImage("imp",   "data/images/imp.png");
        
        imageLibrary.loadImage("pause", "data/images/pause.PNG");
        
        soundLibrary.loadSound("laser", "data/sounds/laser.wav");
        soundLibrary.loadSound("explode", "data/sounds/explode.wav");
        soundLibrary.loadSound("explodemini", "data/sounds/explodemini.wav");
    }

}
