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
import net.awhipple.spacearcaders.gameobjects.UIElement;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Aaron
 */
public class GameState {
    Input input;
    int SCREEN_W, SCREEN_H;
    
    private StarMap starMap;
    
    private List<PlayerShip> playerList;
    
    private List<Actor> actorList;
    private List<Actor> actorsToBeAdded;
    private List<Actor> actorsToBeRemoved;
    
    private ImageLibrary imageLibrary;
    
    private UIElement pauseObject;
    private int pauseRequests = 0;
    
    public GameState(int SCREEN_W, int SCREEN_H) throws Exception {
        this.SCREEN_W = SCREEN_W;
        this.SCREEN_H = SCREEN_H;
        
        playerList = new LinkedList<>();
        
        actorList = new LinkedList<>();
        actorsToBeAdded = new LinkedList<>();
        actorsToBeRemoved = new LinkedList<>();
        
        imageLibrary = new ImageLibrary();
        
        imageLibrary.loadImage("data/proto-laser.PNG", "laser");
        imageLibrary.loadImage("data/proto-ship.PNG", "ship");
        imageLibrary.loadImage("data/pause.PNG", "pause");
        
        pauseObject = null;
    }
    
    public void createStarMap(int screenX, int screenY) throws SlickException {
        starMap = new StarMap(screenX, screenY);
        queueNewActor(starMap);
    }
    
    public void addPlayer(PlayerShip ps) {
        playerList.add(ps);
        queueNewActor(ps);
    }   

    public void addLaser(Laser aLaser) {
       queueNewActor(aLaser);
    }
    
    public void queueNewActor(Actor actor) {
        actorsToBeAdded.add(actor);
    }
    
    public void queueRemoveActor(Actor actor) {
        actorsToBeRemoved.add(actor);
    }
    
    public void updateActorList() {
        while(actorsToBeAdded.size() > 0)
            actorList.add(actorsToBeAdded.remove(0));
        while(actorsToBeRemoved.size() > 0)
            actorList.remove(actorsToBeRemoved.remove(0));
    }
    
    public void setInput(Input input) { this.input = input; }
    public Input getInput() { return input; }
    
    public List<Actor> getActorList() { return actorList; }

    public Image getLibraryImage(String key) { return imageLibrary.getImage(key); }
    
    private void addPauseToScreen(Image pauseImage) {
        if(pauseObject == null) {
            pauseObject = new UIElement(SCREEN_W/2, SCREEN_H/2, pauseImage);
            queueNewActor(pauseObject);
            updateActorList();
        }
    }
    
    public boolean requestPause(Image pauseImage) {
        if(pauseRequests < 5) {
            addPauseToScreen(pauseImage);
            pauseRequests++;
            return false;
        } else {
            return true;
        }
    }
    
    public void unpause() { 
        pauseRequests = 0;
        queueRemoveActor(pauseObject);
        updateActorList();
        pauseObject = null;
    }
}
