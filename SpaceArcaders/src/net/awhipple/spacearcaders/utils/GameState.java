/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.awhipple.spacearcaders.utils;

import java.util.HashMap;
import net.awhipple.spacearcaders.gameobjects.Actor;
import net.awhipple.spacearcaders.gameobjects.StarMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import net.awhipple.spacearcaders.gameobjects.Enemy;
import net.awhipple.spacearcaders.gameobjects.PlayerShip;
import net.awhipple.spacearcaders.gameobjects.Target;
import net.awhipple.spacearcaders.ui.UIImage;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
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
    private List<PlayerShip> playerShipList;
    private Map<String, List<Target>> targetMap;
    private ImageLibrary imageLibrary;
    private SoundLibrary soundLibrary;
    
    private Image pixel;
    
    private UIImage pauseObject;
    
    public GameState(int SCREEN_W, int SCREEN_H, int TARGET_FPS) throws Exception {
        this.SCREEN_W = SCREEN_W;
        this.SCREEN_H = SCREEN_H;
        
        targetDelta = 1d / (double)TARGET_FPS;
        delta = targetDelta;
        
        actorList = new LinkedList<>();
        enemyList = new LinkedList<>();
        playerShipList = new LinkedList<>();
        targetMap = new HashMap<>();
        
        actorsToBeAdded = new LinkedList<>();
        actorsToBeRemoved = new LinkedList<>();
        
        imageLibrary = new ImageLibrary();
        soundLibrary = new SoundLibrary();
        
        pauseObject = null;
        
        loadResources();
        
        pixel = new Image(1, 1);
        Graphics gfx = pixel.getGraphics();
        gfx.setColor(Color.white);
        gfx.drawRect(0, 0, 1, 1);
        gfx.flush();
        
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
            if(actor instanceof Enemy){
                enemyList.add((Enemy) actor);
                addToTargetList((Target)actor, "enemy");
            }
            if(actor instanceof PlayerShip){ 
                playerShipList.add((PlayerShip) actor);
                addToTargetList((Target)actor, "player");
            }
        }
        while(actorsToBeRemoved.size() > 0) {
            Actor actor = actorsToBeRemoved.remove(0);
            actorList.remove(actor);
            if(actor instanceof Enemy) {
                removeFromTargetMap((Target) actor);
                enemyList.remove((Enemy) actor);
            }
            
            if(actor instanceof PlayerShip) {
                removeFromTargetMap((Target) actor);
                playerShipList.remove((PlayerShip) actor);
            }
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
    
    public Image getPixel() { return pixel; }
    
    public int getScreenWidth() { return SCREEN_W; }
    public int getScreenHeight() { return SCREEN_H; }
    
    public void setDeltaFromFps(int fps) {
        if(fps == 0) delta = targetDelta;
        else this.delta = 1d/(double)fps;
    }

    private void createStarMap() throws SlickException {
        queueNewActor(new StarMap(SCREEN_W, SCREEN_H));
    }
    
    public List<Enemy> getEnemyList() { return enemyList; }
    public List<PlayerShip> getPlayerShipList() { return playerShipList; }
    
    public List<Target> getTargetList(String targetType) {
        if(targetMap.containsKey(targetType))
            return targetMap.get(targetType);
        else
            return addTargetList(targetType); 
    }
    
    public void addToTargetList(Target targetToAdd, String targetType) {
            if(!targetMap.containsKey(targetType))
                addTargetList(targetType);
            targetMap.get(targetType).add(targetToAdd);
    }
    private void removeFromTargetMap(Target target) {
         for(String key: targetMap.keySet())
             targetMap.get(key).remove(target);
    }

    private List<Target> addTargetList(String targetType) {
        List<Target> targetList = new LinkedList<>();
        targetMap.put(targetType, targetList);
        return targetList;
    }
    
    private void loadResources() throws Exception {
        imageLibrary.loadImage("ship",          "data/images/proto-ship.PNG");
        imageLibrary.loadImage("laser",         "data/images/proto-laser.PNG");
        
        imageLibrary.loadImage("imp-red",           "data/images/imp-red.png");
        imageLibrary.loadImage("imp-green",           "data/images/imp-green.png");
        imageLibrary.loadImage("imp-blue",           "data/images/imp-blue.png");
        
        imageLibrary.loadImage("pause",         "data/images/pause.PNG");
        imageLibrary.loadImage("particle",      "data/images/particle.png");
        
        soundLibrary.loadSound("laser",         "data/sounds/laser.wav");
        soundLibrary.loadSound("explode",       "data/sounds/explode.wav");
        soundLibrary.loadSound("explodemini",   "data/sounds/explodemini.wav");
    }

  
}
