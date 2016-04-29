package game.gameView;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Hannes on 29/04/2016.
 */
public class PowerupView {

    private Box powerBox;
    private Geometry powerupGeom;
    private Material boxMaterial;

    private AssetManager assetManager;
    private Node rootNode;

    private GroundView groundView;


    private float randomX;
    private float randomZ;

    private final static Random randomGenerator = new Random();


    public PowerupView(Application application, Node rootNode, GroundView groundView){
        this.assetManager = application.getAssetManager();
        this.rootNode = rootNode;
        this.groundView =  groundView;
        powerBox = new Box(1f,1f,1);


    }

    /**sets the possible x and y range for box.*/
    private void setRandomPos(){
        float maximumX = groundView.getGroundShape().getWidth();
        float maximumZ = groundView.getGroundShape().getHeight();

        randomX = randomGenerator.nextInt((int)(maximumX)) - maximumX/2-(powerBox.getXExtent()*2);
        randomZ = randomGenerator.nextInt((int)((maximumZ)))-maximumZ/2-(powerBox.getZExtent()*2);
    }

    /**creates a power up at random position. Have to set type of power up.*/
    public void createPowerup(String type){
        setRandomPos();
        setType(type);
        powerupGeom = new Geometry("Box", powerBox);
        boxMaterial = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        boxMaterial.setColor("Color", ColorRGBA.Yellow);
        powerupGeom.setMaterial(boxMaterial);
        powerupGeom.setLocalTranslation(randomX, 0, randomZ);
        rootNode.attachChild(powerupGeom);

    }

    public void setType(String argument){
        if (argument.equalsIgnoreCase("speed")){
            speed();
        }
        if (argument.equalsIgnoreCase("bullet")){
            bullet();
        }
    }

    private void speed(){

    }
    private void bullet(){

    }
}
