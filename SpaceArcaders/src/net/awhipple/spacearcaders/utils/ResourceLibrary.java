/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.awhipple.spacearcaders.utils;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Aaron
 */
public class ResourceLibrary {
    private ImageLibrary imageLibrary;
    private SoundLibrary soundLibrary;
        
    public ResourceLibrary() throws SlickException {
        imageLibrary = new ImageLibrary();
        soundLibrary = new SoundLibrary();
        
        imageLibrary.loadImage("ship",          "data/images/proto-ship.PNG");
        imageLibrary.loadImage("laser",         "data/images/proto-laser.PNG");
        
        imageLibrary.loadImage("imp-red",       "data/images/imp-red.png");
        imageLibrary.loadImage("imp-green",     "data/images/imp-green.png");
        imageLibrary.loadImage("imp-blue",      "data/images/imp-blue.png");
        
        imageLibrary.loadImage("title",         "data/images/title.png");
        imageLibrary.loadImage("titleText",     "data/images/temptitletext.png");
        imageLibrary.loadImage("particle",      "data/images/particle.png");
        imageLibrary.loadImage("pause",         "data/images/pause.png");
        
        soundLibrary.loadSound("laser",         "data/sounds/laser.wav");
        soundLibrary.loadSound("explode",       "data/sounds/explode.wav");
        soundLibrary.loadSound("explodemini",   "data/sounds/explodemini.wav");
    }
    
    public Image getImage(String key) { return getImage(key, false); }
    
    public Image getImage(String key, boolean flipped) { 
        if(flipped) return imageLibrary.getFlippedImage(key); 
        else return imageLibrary.getImage(key); 
    }
    
    public void playSound(String key) { soundLibrary.getSound(key).play(1f, .02f); }
}
