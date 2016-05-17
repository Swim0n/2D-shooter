package game.gameView;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Quad;
import game.core.HealthPowerUp;
import game.core.PowerUp;
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
    private Node terrainNode;

    private Quad groundShape;
    private GameView gameView;
    private ApplicationAssets appAssets;


        private final static List<PowerupView> POWER_UP_VIEW_LIST = new ArrayList<PowerupView>();




    public PowerupView(ApplicationAssets appAssets){
        this.appAssets = appAssets;
        this.assetManager = appAssets.getAssetManager();
        this.terrainNode = appAssets.getTerrainNode();
        this.groundShape =  appAssets.getGameView().getGroundSize();
        this.gameView = appAssets.getGameView();
        powerBox = new Box(1f,1f,1f);
        powerupGeom = new Geometry("Power up", powerBox);

        //createPowerUp("health");
        //createPowerUp("speed");
    }


    /**creates a power up at random position. Have to set type of power up.*/
    public void createPowerUp(PowerUp powerUpType, float xPos, float zPos){
        //setRandomPos();
        powerupGeom = new Geometry("Box", powerBox);
        boxMaterial = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        setType(powerUpType);
        powerupGeom.setMaterial(boxMaterial);
        powerupGeom.setLocalTranslation(xPos, -1, zPos);
        terrainNode.attachChild(powerupGeom);

        /**
       if (powerUpType instanceof HealthPowerUp){
            PowerUpController powerUpPhy = new PowerUpController(new HealthPowerUp(), appAssets, this);
            powerupGeom.addControl(powerUpPhy);
        }
        if (powerUpType instanceof SpeedPowerUp){
            PowerUpController powerUpPhy = new PowerUpController(new SpeedPowerUp(), appAssets, this);
            powerupGeom.addControl(powerUpPhy);
        }
        */

        POWER_UP_VIEW_LIST.add(this);


    }

    private void setType(PowerUp powerUp){
        if (powerUp instanceof HealthPowerUp){

            boxMaterial.setColor("Color", ColorRGBA.Yellow);
        }
        if (powerUp instanceof SpeedPowerUp){
           boxMaterial.setColor("Color", ColorRGBA.Cyan);
        }
    }


    public Geometry getPowerupGeom(){
        return powerupGeom;
    }

    public GameView getGameView(){
        return this.gameView;
    }


}
