package game.gameView;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Quad;
import game.core.HealthPowerUp;
import game.core.SpeedPowerUp;
import game.ctrl.PowerUpController;
import game.utils.ApplicationAssets;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class PowerupView {

    private Box powerBox;
    private Geometry powerupGeom;
    private Material boxMaterial;

    private AssetManager assetManager;
    private Node stageNode;

    private Quad groundShape;
    private GameView gameView;
    private ApplicationAssets appAssets;

    private float randomX;
    private float randomZ;

    private final static List<PowerupView> POWER_UP_VIEW_LIST = new ArrayList<PowerupView>();

    private final static Random randomGenerator = new Random();


    public PowerupView(ApplicationAssets appAssets){
        this.appAssets = appAssets;
        this.assetManager = appAssets.getAssetManager();
        this.stageNode = appAssets.getStageNode();
        this.groundShape =  appAssets.getGameView().getGroundSize();
        this.gameView = appAssets.getGameView();
        powerBox = new Box(1f,1f,1f);
        powerupGeom = new Geometry("Power up", powerBox);

        createPowerUp("health");
        createPowerUp("speed");
    }

    /**sets the possible x and z range for box.*/
    private void setRandomPos(){
        float maximumX = groundShape.getWidth();
        float maximumZ = groundShape.getHeight();

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
        powerupGeom.setLocalTranslation(randomX, -1, randomZ);
        stageNode.attachChild(powerupGeom);
        //gameView.powerUpCollisionControl(this,powerupGeom, powerUpType);
        if (powerUpType.equalsIgnoreCase("health")){
            PowerUpController powerUpPhy = new PowerUpController(new HealthPowerUp(), appAssets, this);
            powerupGeom.addControl(powerUpPhy);
        }
        if (powerUpType.equalsIgnoreCase("speed")){
            PowerUpController powerUpPhy = new PowerUpController(new SpeedPowerUp(), appAssets, this);
            powerupGeom.addControl(powerUpPhy);
        }
        System.out.println(powerUpType);

        POWER_UP_VIEW_LIST.add(this);


    }

    private void setType(String powerUpType){
        if (powerUpType.equalsIgnoreCase("speed")){

            boxMaterial.setColor("Color", ColorRGBA.Yellow);
        }
        if (powerUpType.equalsIgnoreCase("health")){
           boxMaterial.setColor("Color", ColorRGBA.Cyan);
        }
    }




    public GameView getGameView(){
        return this.gameView;
    }


}
