/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.awhipple.spacearcaders.utils;

import java.util.HashMap;
import java.util.Map;
import org.newdawn.slick.Image;

/**
 *
 * @author Aaron
 */
public class ImageLibrary {
    Map<String, ImageCache> imageMap;
    
    public ImageLibrary() {
        imageMap = new HashMap<>();
    }
    
    public void loadImage(String key, String fileName) throws Exception {
        
        if(imageMap.containsKey(key)) throw new Exception("ERROR: Tried to load an image and save it under pre existing key: " + key);
        
        imageMap.put(key, new ImageCache(new Image(fileName)));
    }
    
    public Image getImage(String key) { return imageMap.get(key).getBaseImage(); }
    public Image getFlippedImage(String key) { return imageMap.get(key).getFlippedImage(); }
    
    private class ImageCache {
        Image baseImage, flippedImage;
        
        public ImageCache(Image baseImage) throws Exception {
            this.baseImage = baseImage;
            flippedImage = null;
        }
        
        public Image getBaseImage() { return baseImage; }
        public Image getFlippedImage() {
            if(flippedImage == null) {
                flippedImage = baseImage.getFlippedCopy(true, true);
            }
            return flippedImage;
        }
    }
    
}
