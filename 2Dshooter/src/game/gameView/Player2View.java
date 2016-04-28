package game.gameView;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;

/**
 * Created by Simon on 2016-04-26.
 */
public class Player2View extends PlayerView {

    public Player2View(AssetManager assetManager, Node rootNode, GameView gameView){
        super(assetManager,rootNode,gameView);
    }

    public void createPlayer(){
        playerShape = new Box(1f,1f,1f);

        player = new Geometry("Box", playerShape);
        playerMaterial = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        player.setLocalTranslation(4,-4f,0);
        playerMaterial.setColor("Color", ColorRGBA.Green);
        player.setMaterial(playerMaterial);
        rootNode.attachChild(player);

    }
}