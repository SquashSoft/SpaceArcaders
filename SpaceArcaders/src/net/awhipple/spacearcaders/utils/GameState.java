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
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Aaron
 */
public class GameState {
    Input input;
    
    private StarMap starMap;
    
    private List<PlayerShip> playerList;
    private List<Actor> actorList;
    private List<Actor> actorsToBeAdded;
    
    private ImageLibrary imageLibrary;
    
    public GameState() throws Exception {
        playerList = new LinkedList<>();
        actorList = new LinkedList<>();
        actorsToBeAdded = new LinkedList<>();
        
        imageLibrary = new ImageLibrary();
        
        imageLibrary.loadImage("data/proto-laser.PNG", "laser");
        imageLibrary.loadImage("data/proto-ship.PNG", "ship");
    }
    
    public void createStarMap(int screenX, int screenY) throws SlickException {
        starMap = new StarMap(screenX, screenY);
        addActor(starMap);
    }
    
    public void addPlayer(PlayerShip ps) {
        playerList.add(ps);
        addActor(ps);
    }   

    public void addLaser(Laser aLaser) {
       addActor(aLaser);
    }
    
    public void addActor(Actor actor) {
        actorsToBeAdded.add(actor);
    }
    
    public void addAllActors() {
        while(actorsToBeAdded.size() > 0)
            actorList.add(actorsToBeAdded.remove(0));
    }
    
    public void setInput(Input input) { this.input = input; }
    public Input getInput() { return input; }
    
    public List<Actor> getActorList() { return actorList; }

    public Image getLibraryImage(String key) { return imageLibrary.getImage(key); }
}
