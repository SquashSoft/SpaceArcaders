/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.awhipple.spacearcaders.utils;

import java.util.HashMap;
import java.util.Map;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

/**
 *
 * @author Aaron
 */
public class SoundLibrary {
    
    Map<String, Sound> soundMap;
    
    public SoundLibrary() {
        soundMap = new HashMap<>();
    }
    
    public void loadSound(String key, String fileName) throws SlickException {
        
        if(soundMap.containsKey(key)) throw new SlickException("ERROR: Tried to load a sound and save it under pre existing key: " + key);
        
        soundMap.put(key, new Sound(fileName));
    }
    
    public Sound getSound(String key) { return soundMap.get(key); }
}
