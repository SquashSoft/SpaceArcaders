/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.awhipple.spacearcaders.utils;

import java.util.HashMap;
import java.util.Map;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Aaron
 */
public class MusicLibrary {
    
    Map<String, Music> musicMap;
    
    public MusicLibrary() {
        musicMap = new HashMap<>();
    }
    
    public void loadMusic(String key, String fileName) throws SlickException {
        
        if(musicMap.containsKey(key)) throw new SlickException("ERROR: Tried to load a music file and save it under pre existing key: " + key);
        
        Music newFile = new Music(fileName);
        musicMap.put(key, new Music(fileName));
    }
    
    public Music getMusic(String key) { return musicMap.get(key); }
}
