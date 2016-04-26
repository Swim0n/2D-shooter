package game.gameView;

import com.jme3.asset.AssetManager;
import com.jme3.input.InputManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;

/**
 * Created by Simon on 2016-04-26.
 */
public abstract class PlayerView {
    protected Geometry player;
    protected Box playerShape;
    protected Material playerMaterial;
    protected AssetManager assetManager;
    protected GameView gameView;
    protected Node rootNode;
    protected InputManager inputManager;

    public PlayerView(AssetManager assetManager, Node rootNode, GameView gameView){
        this.assetManager = assetManager;
        this.gameView = gameView;
        this.rootNode = rootNode;
        this.inputManager = gameView.getInputManager();
    }

    public void createPlayer(){
    }

    public InputManager getInputManager(){
        return this.inputManager;
    }

    public Geometry getPlayer(){
        return this.player;
    }
}
