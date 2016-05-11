package game.gameView;

import com.jme3.app.Application;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import game.core.HealthPowerUp;
import game.core.PowerUp;
import game.core.SpeedPowerUp;
import game.ctrl.PowerUpController;

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
    private GameView gameView;

    private float randomX;
    private float randomZ;

    private final static List<PowerupView> powerupViewList = new ArrayList<PowerupView>();

    private final static Random randomGenerator = new Random();


    public PowerupView(GameView gameView, Node rootNode, GroundView groundView){
        this.assetManager = gameView.getAssetManager();
        this.rootNode = rootNode;
        this.groundView =  groundView;
        this.gameView = gameView;
        powerBox = new Box(1f,1f,1f);
        powerupGeom = new Geometry("Power up", powerBox);



    }

    /**sets the possible x and z range for box.*/
    private void setRandomPos(){
        float maximumX = groundView.getGroundShape().getWidth();
        float maximumZ = groundView.getGroundShape().getHeight();

        randomX = randomGenerator.nextInt((int)(maximumX)) - maximumX/2;
        randomZ = randomGenerator.nextInt((int)(maximumZ)) - maximumZ/2;
    }

    /**creates a power up at random position. Have to set type of power up.*/
    public void createPowerUp(String powerUpType){
        setRandomPos();
        powerupGeom = new Geometry("Box", powerBox);
        boxMaterial = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        setType(powerUpType);
        powerupGeom.setMaterial(boxMaterial);
        powerupGeom.setLocalTranslation(randomX, 0, randomZ);
        rootNode.attachChild(powerupGeom);
        //gameView.powerUpCollisionControl(this,powerupGeom, powerUpType);
        if (powerUpType.equalsIgnoreCase("health")){
            new PowerUpController(this,new HealthPowerUp(),gameView);
        }
        if (powerUpType.equalsIgnoreCase("speed")){
            new PowerUpController(this,new SpeedPowerUp(),gameView);
        }
        System.out.println(powerUpType);
        powerupViewList.add(this);


    }

    public void setType(String powerUp){
        if (powerUp.equalsIgnoreCase("speed")){

            boxMaterial.setColor("Color", ColorRGBA.Yellow);
        }
        if (powerUp.equalsIgnoreCase("health")){
           boxMaterial.setColor("Color", ColorRGBA.Cyan);
        }
    }


    public GameView getGameView(){
        return this.gameView;
    }

    public Spatial getPowerGeometry(){
        return this.powerupGeom;
    }
}
