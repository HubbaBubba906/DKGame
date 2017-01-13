/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gdx.menu.Screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 *
 * @author gomeh0651
 */
public class Platform {
    private float fX, fY, fWidth, fHeight;
    private SpriteBatch batch;
    private Sprite spPlat;
    public Platform (float fX, float fY, float fWidth, float fHeight, SpriteBatch batch) {
        this.fX = fX;
        this.fY = fY;
        this.fWidth = fWidth;
        this.fHeight = fHeight;
        this.batch = batch;
        spPlat = new Sprite (new Texture("platform.png"));
    }
    
    public void display () {
        batch.begin();
        batch.draw(spPlat, fX, fY);
        batch.end();
    }
    
    public void isHit (Sprite spTarget) {
        
    }    
}
