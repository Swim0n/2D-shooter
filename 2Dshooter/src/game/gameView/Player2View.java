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

    public Player2View(AssetManager assetManager, Node playerNode, GameView gameView){
        super(assetManager,playerNode,gameView);
    }

    public void createPlayer(){
        playerShape = new Box(1f,1f,1f);

        player = new Geometry("Box", playerShape);
        playerMaterial = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        player.setLocalTranslation(4,-2f,0);
        playerMaterial.setColor("Color", ColorRGBA.Blue);
        player.setMaterial(playerMaterial);
        playerNode.attachChild(player);

    }
}
