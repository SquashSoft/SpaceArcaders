/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.awhipple.spacearcaders.ui;

import net.awhipple.spacearcaders.gameobjects.PlayerShip;
import net.awhipple.spacearcaders.views.GameField;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Aaron
 */
public class UIPlayerBombAmmo extends UIBar {
    
    PlayerShip player;
    
    public UIPlayerBombAmmo(PlayerShip player, int x, int y) throws SlickException {
        super(player.getBombAmmo(), x, y);
        
        this.player = player;
    }
    
    @Override
    public void update(GameField gf) {
        super.update(gf);
        
        super.setValue(player.getBombAmmo());
    }
}
