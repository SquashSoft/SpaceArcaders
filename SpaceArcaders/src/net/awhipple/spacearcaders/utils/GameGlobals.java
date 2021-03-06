/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.awhipple.spacearcaders.utils;

import net.awhipple.spacearcaders.gameobjects.StarMap;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Aaron
 */
public class GameGlobals {
    int screenWidth, screenHeight, fps;
    double delta;
    ResourceLibrary resourceLibrary;
    
    StarMap starMap;
    
    public GameGlobals(int screenWidth, int screenHeight, int fps) throws SlickException {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.fps = fps;
        this.delta = 1f/fps;
        
        resourceLibrary = new ResourceLibrary();
        
        starMap = new StarMap();
        starMap.init(screenWidth, screenHeight);
    }
    
    public GameGlobals(int screenWidth, int screenHeight, int fps, ResourceLibrary resourceLibrary, StarMap starMap) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.fps = fps;
        this.delta = 1f/fps;
        this.resourceLibrary = resourceLibrary;
        this.starMap = starMap;
    }
    
    public GameGlobals copy() {
        return new GameGlobals(screenWidth, screenHeight, fps, resourceLibrary, starMap);
    }
    
    public int getScreenWidth() { return screenWidth; }
    public int getScreenHeight() { return screenHeight; }
    public double getDelta() { return delta; }
    
    public StarMap getStarMap() { return starMap; }
    
    public Image getImage(String key) { return resourceLibrary.getImage(key); }
    public Image getImage(String key, boolean flipped) { return resourceLibrary.getImage(key, flipped); }
    public void playSound(String key) { resourceLibrary.playSound(key); }
    public void playMusic(String key) { resourceLibrary.playMusic(key); }
}
