package game.gameView;

import com.jme3.asset.AssetManager;
import com.jme3.input.InputManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import game.ctrl.PlayerController;

/**
 * Created by Hannes on 24/04/2016.
 */
public class PlayerView {

    private Geometry player;
    private Box playerShape;
    private Material playerMaterial;
    private AssetManager assetManager;
    private PlayerController playerControl;
    private GameView gameView;
    private Node rootNode;
    private InputManager inputManager;

    public PlayerView(AssetManager assetManager, Node rootNode, GameView gameView){
        this.assetManager = assetManager;
        this.gameView = gameView;
        this.rootNode = rootNode;
    }

    public void createPlayer(){
        playerShape = new Box(1,1,1);
        playerControl = new PlayerController(this, 1f, 1f, 1f);

        player = new Geometry("Box", playerShape);
        playerMaterial = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        player.setLocalTranslation(0,0.5f,0);
        playerMaterial.setColor("Color", ColorRGBA.Red);
        player.setMaterial(playerMaterial);
        rootNode.attachChild(player);
        player.addControl(playerControl);
        //bulletAppState.getPhysicsSpace().add(playerControl);
    }

    public InputManager getInputManager(){
        return gameView.getInputManager();
    }

    public Geometry getPlayer(){
        return this.player;
    }
}
