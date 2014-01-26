/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.awhipple.spacearcaders.utils;

import java.util.HashMap;
import java.util.Map;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;

/**
 *
 * @author Aaron
 */
public class ImageLibrary {
    public static Color TRANS_COLOR = new Color(255, 0, 255, 255);
    
    Map<String, Image> imageMap;
    
    public ImageLibrary() {
        imageMap = new HashMap<>();
    }
    
    public void loadImage(String key, String fileName) throws Exception {
        
        if(imageMap.containsKey(key)) throw new Exception("ERROR: Tried to load an image and save it under pre existing key: " + key);
        
        imageMap.put(key, new Image(fileName, TRANS_COLOR));
    }
    
    public Image getImage(String key) { return imageMap.get(key); }
    
}
