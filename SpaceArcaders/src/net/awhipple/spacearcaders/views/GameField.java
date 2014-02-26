/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.awhipple.spacearcaders.views;

import java.util.Collections;
import java.util.HashMap;
import net.awhipple.spacearcaders.gameobjects.Actor;
import net.awhipple.spacearcaders.gameobjects.StarMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import net.awhipple.spacearcaders.gameobjects.Enemy;
import net.awhipple.spacearcaders.gameobjects.PlayerShip;
import net.awhipple.spacearcaders.gameobjects.Target;
import net.awhipple.spacearcaders.ui.UIPlayerHealthBar;
import net.awhipple.spacearcaders.utils.ResourceLibrary;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Aaron
 */
public final class GameField implements View{
    private Input input;
    private int SCREEN_W, SCREEN_H;
    private double delta, targetDelta;
    
    private List<Actor> actorList;
    private List<Actor> actorsToBeAdded;
    private List<Actor> actorsToBeRemoved;
    private List<Enemy> enemyList;
    private List<PlayerShip> playerShipList;
    private Map<String, List<Target>> targetMap;
    
    private Image pixel;
    private ResourceLibrary resourceLibrary;
    
    int numEnemies = 0;
    
    public GameField(ResourceLibrary resourceLibrary, int SCREEN_W, int SCREEN_H, int TARGET_FPS) throws SlickException {
        this.resourceLibrary = resourceLibrary;
        
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
        
        pixel = new Image(1, 1);
        Graphics gfx = pixel.getGraphics();
        gfx.setColor(Color.white);
        gfx.drawRect(0, 0, 1, 1);
        gfx.flush();
        
        createStarMap();
        
        processActorQueues();
        
        PlayerShip player1 = new PlayerShip(SCREEN_W/4,3*SCREEN_H/4, resourceLibrary.getImage("ship") );
        player1.setKeys(Input.KEY_W, Input.KEY_S, Input.KEY_A, Input.KEY_D, Input.KEY_G);
        queueNewActor(player1);
        
        PlayerShip player2 = new PlayerShip(3*SCREEN_W/4,3*SCREEN_H/4, resourceLibrary.getImage("ship") );
        player2.setKeys(Input.KEY_UP, Input.KEY_DOWN, Input.KEY_LEFT, Input.KEY_RIGHT, Input.KEY_RCONTROL);
        queueNewActor(player2);
        
        queueNewActor(new UIPlayerHealthBar(player1, 100, SCREEN_H-20));
        queueNewActor(new UIPlayerHealthBar(player2, SCREEN_W-200, SCREEN_H-20));
        
        processActorQueues();
    }

    @Override
    public List<ViewInstruction> update(Input input) {
        if(enemyList.isEmpty()) {
            numEnemies++;
            for(int i = 0; i < numEnemies; i++) {
                Enemy enemy = new Enemy((int)(Math.random()*SCREEN_W), -300, resourceLibrary.getImage("imp-red", true));
                queueNewActor(enemy);
            }
        }

        setInput(input);
        
        updateActors();
        
        if(input.isKeyDown(Input.KEY_P)) return Collections.singletonList(new ViewInstruction(ViewInstruction.Set.SWITCH_VIEW, new Pause(this, resourceLibrary.getImage("pause"), SCREEN_W, SCREEN_H)));
        return null;
    }
    
    public void updateActors() {
        for(Actor curActor : actorList) {
            curActor.update(this);
        }
        processActorQueues();
    }
    
    @Override
    public void render() {
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
            try {
                actor.init(this);
            } catch (SlickException ex) {
                System.out.println("WARNING! Unable to init actor!!! " + actor);
                System.out.println(ex.getMessage());
            }
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

    public void setInput(Input input) { this.input = input; }
    public Input getInput() { return input; }

    public double getDelta() { return delta; }
    
    public Image getPixel() { return pixel; }
    
    public int getScreenWidth() { return SCREEN_W; }
    public int getScreenHeight() { return SCREEN_H; }
    
    public void setDeltaFromFps(int fps) {
        if(fps == 0) delta = targetDelta;
        else this.delta = 1d/(double)fps;
    }

    private void createStarMap() throws SlickException {
        queueNewActor(new StarMap());
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
    
    public ResourceLibrary getResLib() { return resourceLibrary; }
}
